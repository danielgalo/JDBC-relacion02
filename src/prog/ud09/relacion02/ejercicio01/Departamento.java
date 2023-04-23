package prog.ud09.relacion02.ejercicio01;

public class Departamento {

	private int numde;
	private int numce;
	private String direc;
	private int tidir;
	private int presu;
	private int depde;
	private String nomde;
	


	public Departamento(int numde, int numce, String direc, int tidir, int presu, int depde, String nomde) {
		this.numde = numde;
		this.numce = numce;
		this.direc = direc;
		this.tidir = tidir;
		this.presu = presu;
		this.depde = depde;
		this.nomde = nomde;
	}

	public int getNumde() {
		return numde;
	}

	public void setNumde(int numde) {
		this.numde = numde;
	}

	public int getNumce() {
		return numce;
	}

	public void setNumce(int numce) {
		this.numce = numce;
	}

	public String getDirec() {
		return direc;
	}

	public void setDirec(String direc) {
		this.direc = direc;
	}

	public int getTidir() {
		return tidir;
	}

	public void setTidir(int tidir) {
		this.tidir = tidir;
	}

	public int getPresu() {
		return presu;
	}

	public void setPresu(int presu) {
		this.presu = presu;
	}

	public int getDepde() {
		return depde;
	}

	public void setDepde(int depde) {
		this.depde = depde;
	}

	public String getNomde() {
		return nomde;
	}

	public void setNomde(String nomde) {
		this.nomde = nomde;
	}

	@Override
	public String toString() {
		return "Departamento [numde=" + numde + ", numce=" + numce + ", direc=" + direc + ", tidir=" + tidir + ", presu="
				+ presu + ", depde=" + depde + ", nomde=" + nomde + "]";
	}
	
	
	
	
}
