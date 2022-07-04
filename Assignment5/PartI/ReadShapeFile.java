import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;
import shapes.*;

/* your tasks:
 * create a class called ShapeException
 * createShape should throw a ShapeException
 * in main(), you should catch the ShapeException
 * 
 */
public class ReadShapeFile {

	public static GeometricObject createShape(String shapeName) throws ShapeException {

		/* if shapeName is "Circle" return Circle();
		 * if shapeName is "Square" return Square();
		 * if shapeName is "Rectangle" return Rectangle();
		 * if it is not any one of these, it should throw
		 * a ShapeException
		 */
		if (Objects.equals(shapeName, "Circle"))
			return new Circle();
		else if (Objects.equals(shapeName, "Square"))
			return new Square();
		else if (Objects.equals(shapeName, "Rectangle"))
			return new Rectangle();
		else
			throw new ShapeException(shapeName);
	}

	public static void main(String[] args) {
		ArrayList<GeometricObject> shapeList = new ArrayList<GeometricObject>();
		File f = new File("shapes.txt");
		String inString = null;
		FileReader fr = null;
		BufferedReader in;
		try {
			fr = new FileReader(f);
			in = new BufferedReader(fr);
			inString = in.readLine();
			while (inString != null) {
				try {
					GeometricObject shape = createShape(inString);
					shapeList.add(shape);
				} catch (ShapeException se) {
					System.err.println("Invalid Shape Exception:" + se.getInvalidName());
				}
				inString = in.readLine();
			}
		} catch (FileNotFoundException fne) {
			System.err.println("IO Exception Not Found this file:" + fne.toString());
		} catch (IOException ioe) {
			System.err.println("IO Exception reading file:" + ioe.toString());
		}
		finally {
			try {
				fr.close();
				System.out.println("Closing FileReader");
			} catch (IOException e) {
				System.err.println("IO Exception closing file");
			}
		}
		Iterator iterator = shapeList.iterator();
		while(iterator.hasNext())
		System.out.println(iterator.next());
		System.out.println(shapeList.size());
	}
	/* create a loop to read the file line-by-line */

//		try {
//
//			GeometricObject shape = createShape(inString);
//		} catch (/* your exception */ ) {
//			System.err.println("Cannot create shape: " + inString);
//		}


}
		


