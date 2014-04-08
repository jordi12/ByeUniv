package bd;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class LigneServiceImpl implements LigneService {

	@Autowired
	LigneDao lignedao;

	@Override
	public List<Ligne> getLigneList() {
		return lignedao.getLigneList();
	}
	
	@Override
	public void updateData(Ligne ligne) {
		lignedao.updateData(ligne);
		
	}
	
	@Override
	public void deleteData(String id) {
		lignedao.deleteData(id);
		
	}

	
	@Override
	public Ligne getLigne(String id) {
		return lignedao.getLigne(id);
	}
	
	@Override
	public void insertData(Ligne ligne) {
		lignedao.insertData(ligne);
	}

	
	


	
}
