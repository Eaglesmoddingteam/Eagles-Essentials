package btf.util.helpers;

public class ArrayHelper {

	private ArrayHelper(){

	}

	public static <T> boolean includes(T[] array, T t) {
		for (T t1 : array) {
			if (t1.equals(t)) {
				return true;
			}
		}
		return false;
	}
}
