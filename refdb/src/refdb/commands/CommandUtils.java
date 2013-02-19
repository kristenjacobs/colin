package refdb.commands;

import java.util.Collection;
import java.util.Map;

import jcolin.consoles.IConsole;
import jcolin.utils.TypeUtils;
import refdb.Model;
import refdb.Reference;

public class CommandUtils {

	/**
	 * This method returns the refid for the given argument,
	 * unless the arg value is "-" in which case it returns 
	 * the refid of the most recently created reference.
	 */
	public static int getRefId(Map<String, String> args, Model model) {
		String refId = args.get("refid"); 
		if ((refId != null) && refId.equals("-")) {
			return model.getLastRefId();
		}
		return TypeUtils.getInteger(args, "refid");
	}

	/**
	 * This method returns the authorid for the given argument,
	 * unless the arg value is "-" in which case it returns 
	 * the authorid of the most recently created author.
	 */
	public static int getAuthorId(Map<String, String> args, Model model) {
		String authorId = args.get("authorid"); 
		if ((authorId != null) && authorId.equals("-")) {
			int refId = getRefId(args, model);
			Reference reference = model.getReference(refId);
			return reference.getLastAuthorId();
		}
		return TypeUtils.getInteger(args, "authorid");
	}

	/**
	 * This method returns the infoid for the given argument,
	 * unless the arg value is "-" in which case it returns 
	 * the infoid of the most recently created author.
	 */
	public static int getInfoId(Map<String, String> args, Model model) {
		String infoId = args.get("infoid"); 
		if ((infoId != null) && infoId.equals("-")) {
			int refId = getRefId(args, model);
			Reference reference = model.getReference(refId);
			return reference.getLastInfoId();
		}
		return TypeUtils.getInteger(args, "infoid");
	}
	
	/**
	 * This method displays a list of ids on the console
	 * in a defined format (ints separated by spaces).
	 */
	public static void outputIds(IConsole console, Collection<Integer> ids) {
		boolean first = true;
		for (int id : ids) {
			if (!first) {
				console.display(" ");
			}
			console.display(String.format("%d", id));
			first = false;
		}
		if (ids.size() > 0) {
			console.display("\n");			
		}		
	}
}
