package jcolin.completors;

import java.util.List;

import jline.Completor;
import jline.ArgumentCompletor.ArgumentDelimiter;
import jline.ArgumentCompletor.WhitespaceArgumentDelimiter;

public class JColinArgumentCompletor implements Completor {
    private final List<Completor> m_completors;
    private final ArgumentDelimiter m_delim;    
    private String m_buffer;

    public JColinArgumentCompletor(final List<Completor> completors) {
        m_completors = completors;
        m_delim = new WhitespaceArgumentDelimiter();
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public int complete(final String buffer, final int cursor,
                        final List candidates) {
        jline.ArgumentCompletor.ArgumentList list = m_delim.delimit(buffer, cursor);
        int argpos = list.getArgumentPosition();
        int argIndex = list.getCursorArgumentIndex();
        m_buffer = buffer;        

        if (argIndex < 0) {
            return -1;
        }

        final Completor comp;

        // if we are beyond the end of the completors, just use the last one
        if (argIndex >= m_completors.size()) {
            comp = m_completors.get(m_completors.size() - 1);
        } else {
            comp = m_completors.get(argIndex);
        }
        int ret = comp.complete(list.getCursorArgument(), argpos, candidates);

        if (ret == -1) {
            return -1;
        }

        int pos = ret + (list.getBufferPosition() - argpos);

        /**
         *  Special case: when completing in the middle of a line, and the
         *  area under the cursor is a delimiter, then trim any delimiters
         *  from the candidates, since we do not need to have an extra
         *  delimiter.
         *
         *  E.g., if we have a completion for "foo", and we
         *  enter "f bar" into the buffer, and move to after the "f"
         *  and hit TAB, we want "foo bar" instead of "foo  bar".
         */
        if ((cursor != buffer.length()) && m_delim.isDelimiter(buffer, cursor)) {
            for (int i = 0; i < candidates.size(); i++) {
                String val = candidates.get(i).toString();

                while ((val.length() > 0)
                    && m_delim.isDelimiter(val, val.length() - 1)) {
                    val = val.substring(0, val.length() - 1);
                }

                candidates.set(i, val);
            }
        }
        return pos;
    }
    
    public String getBuffer() {
        return m_buffer;
    }
}