package localhost.sandbox.jse8.A0Helper;

import java.util.HashSet;
import java.util.Set;

/**
 * <h1>Join Helper.
 * 
 * <p>For two collections, lets call them "left" and "right",
 * this class provides a function to obtain "leftOuterJoin",
 * "innerJoin" and "rightOuterJoin" element collections.
 * 
 * <p>For this helper to work properly, elements in Set<T> 
 * must be unique.  This is achieved if 'T' extends auto-boxing 
 * types (String, Boolean, Integer, Long, Float, Double).
 * 
 * <p>For other types, it is necessary to define overriding 
 * functions {@link Object#hashCode()} and 
 * {@link Object#equals(Object)}, in order to 
 * Set<T> to contain unique elements.
 * 
 * <p>It is also very useful for troubleshooting if type 'T' 
 * and all its attributes types have a convenient definition 
 * of {@link Object#toString()}.
 * 
 * @author Alberto Romero
 * @since 2024-05-30
 * 
 */
public class JoinHelper {

	public static void main() {
		System.out.println("Hello from JoinHelper!");
		// left
		Set<String> left = new HashSet<>();
		left.add("left00");
		left.add("left01");
		left.add("inner00");
		left.add("inner01");
		// right
		Set<String> right = new HashSet<>();
		right.add("inner00");
		right.add("inner01");
		right.add("right00");
		right.add("right01");
		// result
		JoinResultPojo<String> jr = getJoinResult(left, right);
		System.out.println("JoinResult.toString(): " + jr);
		System.out.println("JoinResult.toStringLarge(): " + jr.toStringLarge());
	}



	/**
	 * <p>See comment regarding type 'T' on this class main description. 
	 * 
	 */
	public static <T> JoinResultPojo<T> getJoinResult(Set<T> left, Set<T> right) {
		// vars
		Set<T> leftOuterJoin = new HashSet<>();
		Set<T> innerJoin = new HashSet<>();
		Set<T> rightOuterJoin = new HashSet<>();
		JoinResultPojo<T> joinResult = new JoinResultPojo<T>();
		// innerJoin
		innerJoin.addAll(left); 
		innerJoin.retainAll(right);
		// leftOuterJoin
		for (T e : left) {
			if (!innerJoin.contains(e)) leftOuterJoin.add(e);
		}
		// rightOuterJoin
		for (T e : right) {
			if (!innerJoin.contains(e)) rightOuterJoin.add(e);
		}
		// joinResult
		joinResult.setLeft(left);
		joinResult.setRight(right);
		joinResult.setLeftOuterJoin(leftOuterJoin);
		joinResult.setInnerJoin(innerJoin);
		joinResult.setRightOuterJoin(rightOuterJoin);
		return joinResult;
	}

	public static class JoinResultPojo<T> {

		Set<T> left;
		Set<T> right;
		Set<T> leftOuterJoin;
		Set<T> innerJoin;
		Set<T> rightOuterJoin;

		public JoinResultPojo () {
			super();
		}

		public Set<T> getLeft() {
			return left;
		}

		public void setLeft(Set<T> left) {
			this.left = left;
		}

		public Set<T> getRight() {
			return right;
		}

		public void setRight(Set<T> right) {
			this.right = right;
		}

		public Set<T> getLeftOuterJoin() {
			return leftOuterJoin;
		}

		public void setLeftOuterJoin(Set<T> leftOuterJoin) {
			this.leftOuterJoin = leftOuterJoin;
		}

		public Set<T> getRightOuterJoin() {
			return rightOuterJoin;
		}

		public void setRightOuterJoin(Set<T> rightOuterJoin) {
			this.rightOuterJoin = rightOuterJoin;
		}

		public Set<T> getInnerJoin() {
			return innerJoin;
		}

		public void setInnerJoin(Set<T> innerJoin) {
			this.innerJoin = innerJoin;
		}

		public String toString() {
			String out = "{ "
					+ "" + "left: " + this.left.size()
					+ ", " + "right: " + this.right.size()
					+ ", " + "leftOuterJoin: " + this.leftOuterJoin.size()
					+ ", " + "innerJoin: " + this.innerJoin.size()
					+ ", " + "rightOuterJoin: " + this.rightOuterJoin.size()
					+ " }"
					;
			return out;
		}

		public String toStringLarge() {
			String out = "{ "
					+ "" + "left: " + this.left
					+ ", " + "right: " + this.right
					+ ", " + "leftOuterJoin: " + this.leftOuterJoin
					+ ", " + "innerJoin: " + this.innerJoin
					+ ", " + "rightOuterJoin: " + this.rightOuterJoin
					+ " }"
					;
			return out;
		}
	}
}
