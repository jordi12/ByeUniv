package bd;

import java.util.List;

public interface LigneService {
	public void insertData(Ligne ligne);

	public List<Ligne> getLigneList();

	public void deleteData(String id);
	
	public Ligne getLigne(String id);

	public void updateData(Ligne ligne);
}
