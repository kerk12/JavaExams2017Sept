/**
 * Represents one (1) good person, and contains all of his/her details.
 * @author kerk12
 *
 */
public class Forofygas {
	String name, address, tel, for_m, poin_m;
	
	// Position in the details file
	int details_pos;
	
	// Existing and new debt
	double xreos, xreos_new;

	public Forofygas(String name, String address, String tel, int details_pos) {
		super();
		this.name = name;
		this.address = address;
		this.tel = tel;
		this.details_pos = details_pos;
	}
}
