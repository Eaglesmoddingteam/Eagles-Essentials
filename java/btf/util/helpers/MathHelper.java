package btf.util.helpers;

public class MathHelper {


	/**
	 * Get Positive Outcome
	 *
	 * @return positivce version
	 */
	public static int GPO(int i) {
		if (i < 1)
			return i;
		else
			return -1 * i;
	}

}
