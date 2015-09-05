package edu.gatech.oad.antlab.person;

/**
 *  A simple class for person 5
 *  returns their name and a
 *  modified string 
 *  
 *  @author Bob
 *  @version 1.1
 */
public class Person5 {
  /** Holds the persons real name */
  private String name;
  	/**
	 * The constructor, takes in the persons
	 * name
	 * @param pname the person's real name
	 */
  public Person5(String pname) {
    name = pname;
  }
  	/**
	 * This method should take the string
	 * input and return its characters rotated
	 * 2 positions.
	 * given "gtg123b" it should return
	 * "g123bgt".
	 *
	 * @param input the string to be modified
	 * @return the modified string
	 */
	private String calc(String input) {
		//Person 5 put your implementation here
		if (input == null) {
			throw new java.lang.IllegalArgumentException("input can't be null");
		}
		if (input.length() == 1) {
			return input;
		}

		char first = input.charAt(0);
		char second = input.charAt(1);
		String newStr = input.substring(2);
		newStr = newStr + first + second;

		return newStr;
	}

		/**
         * Return a string rep of this object
         * that varies with an input string
         *
         * @param input the varying string
         * @return the string representing the
         *         object
         */
	public String toString(String input) {
	  return name + calc(input);
	}

}
