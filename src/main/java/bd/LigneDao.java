package bd;

import java.util.List;

public interface LigneDao {

	public List<Ligne> getLigneList();

	public void updateData(Ligne ligne);

	public Ligne getLigne(String id);
	
	public void insertData(Ligne ligne);  
	 
	public void deleteData(String id);  


}
