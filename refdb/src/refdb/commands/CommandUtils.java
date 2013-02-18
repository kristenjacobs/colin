package refdb.commands;

import java.util.Map;

import refdb.Model;

import jcolin.utils.TypeUtils;

public class CommandUtils {

	/**
	 * This method returns the refid for the given argument,
	 * unless the arg value is "-" in which case it returns 
	 * the refid of the most recently created reference.
	 */
	public static int getRefId(Map<String, String> args, Model model) {
		String refid = args.get("refid"); 
		if ((refid != null) && refid.equals("-")) {
			return model.getLastRefId();
		}
		return TypeUtils.getInteger(args, "refid");
	}
}
