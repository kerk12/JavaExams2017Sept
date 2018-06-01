import java.io.EOFException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;

public class ForofygadesMain {

	/**
	 * List of good people.
	 */
	private static List<Forofygas> forofygades_c = new ArrayList<Forofygas>();
	
	private static RandomAccessFile forofygades;
	private static RandomAccessFile details;
	private static RandomAccessFile xreos_db;
	private static FileWriter fw;
	
	/**
	 * Reads a string through a RAF.
	 * @param length The length in bytes of the string
	 * @param rafGiven The RAF you want to read a string from.
	 * @return The string that was read.
	 * @throws IOException When it gets fucked up.
	 */
	private static String readString(int length, RandomAccessFile rafGiven) throws IOException{
		byte b[] = new byte[length];
		rafGiven.readFully(b);
		return new String(b);
	}
	
	
	public static void main(String[] args) throws IOException {
		//Instantiate the files.
		forofygades = new RandomAccessFile("forofygades.dbs","r");
		details = new RandomAccessFile("details.dbs","r");
		xreos_db = new RandomAccessFile("xreos.dbs", "r");
		
		//Index for reading forofygades.dbs
		int index_foro = 1;
		
		try {
			while(true) {
				// 1. Read the forofygades file for the first set of details.
				forofygades.seek((50+60+50+4)*(index_foro - 1));  //Position of the current record in bytes
				String name = readString(50, forofygades);
				String address = readString(60, forofygades);
				String tel = readString(50, forofygades);
				int det_pos = forofygades.readInt();
				// Create a new forofygas object
				Forofygas fucker = new Forofygas(name, address, tel, det_pos);
				
				// 2. Read details.dbs and get the rest of the details.
				details.seek((8+50+60) * (fucker.details_pos - 1));
				double xreos = details.readDouble();
				String fm = readString(50, details);
				String pm = readString(60, details);
				fucker.for_m = fm;
				fucker.xreos = xreos;
				fucker.poin_m = pm;
				// 3. Add to the list of good people.
				forofygades_c.add(fucker);
				
				index_foro++;
			}
		} catch(EOFException e) {}
		
		// READ XREOS_DBS
		
		int index_xr = 1;
		try {
			while(true) {
				xreos_db.seek((8+4) * (index_xr - 1));
				int code = xreos_db.readInt();
				// Get the fucker from the list.
				Forofygas fucker = forofygades_c.get(code - 1);
				// Set his new debt.
				fucker.xreos_new = xreos_db.readDouble();
				// Update the list.
				forofygades_c.set(code-1, fucker);
				index_xr++;
			}
		} catch(EOFException e2) {}
		
		fw = new FileWriter(new File("fylaki.txt"));
		for (Forofygas fucker : forofygades_c) {
			// If both debts are GT 30.000, send him to jail...
			if (fucker.xreos > 30000.0 && fucker.xreos_new > 30000.0) { 
				fw.write(fucker.name);
				fw.write(fucker.address);
				fw.write(fucker.poin_m);
				fw.write(String.valueOf(fucker.xreos_new));
				fw.write(System.lineSeparator());
			}
		}

		
	}

}
