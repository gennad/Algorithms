import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * This snipplet generates the position of input link for url shortener.
 * For example, if you want to short your url and wonder,
 * at what position will be your url, you can run this program and find out it.
 * For example, the url "abc" will be at 81 position, url "c" will be on the third,
 * url "aa" will be on 27th, url "ab" will be on the 28th etc.
 * 
 * @author gennad
 */
public class UrlShortenerPos {

	/**
	 * Entry point of the app
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Please enter a string you want to generate:");
		String string = scanner.next();
		int r;
		try {
			r = getNumber(string);
			System.out.format("Your string will be on %d position%n", r);
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
		} catch (InvalidStringException e) {
			System.out.println(e.getMessage());
		}
		
	}
	
	/**
	 * Calculates the position of the url
	 * 
	 * @param string url
	 * @return position
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 */
	static int getNumber(String string) throws InvalidStringException, FileNotFoundException, UnsupportedEncodingException  {
		
		Pattern pattern = Pattern.compile("^[A-Za-z]+$");
		Matcher matcher = pattern.matcher(string);
		if (!matcher.find()) {
			Formatter formatter = new Formatter(new StringBuilder(), Locale.US);
			formatter.format("You typed: \"%s\". This is NOT string in latin!", string);
			throw new InvalidStringException(formatter.toString());
		}
		
		final int MAX_LETTERS = 26;
		if (string.length() == 0) return 0;
		Integer[] total = new Integer[string.length()];
		char[] charArray = string.toCharArray();
		Stack<Character> stack = new Stack();
		for (int i =charArray.length; i!=0 ; i--) {
			stack.add(charArray[i-1]);
		}
		int i=0;
		do {
			int j=0;			
			Character character = stack.pop();
			for (char letter='a';  letter<='z'; letter++) {
				if (character == letter || character.toUpperCase(character) == letter) {
					if (stack.size() != 0) {
						total[i] = MAX_LETTERS*(j+1);
						break;
					} else {
						total[i] = j+1;
					}
				}
				j++;
			}
			i ++;
		} while (stack.size() != 0);
		
	    int result=0;
	    for (int num: total) {
	    	result += num;
	    }
	    return result;
	}
}

class InvalidStringException extends Exception {
	public InvalidStringException(String name) {
		super(name);
	}
	
	public InvalidStringException() {
		super();
	}
}
