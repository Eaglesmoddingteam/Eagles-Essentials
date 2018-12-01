package btf.objects.blocks.tiles;

import java.util.ArrayList;
import java.util.List;

public class CableNetwork {

	final List<TileCable> cables;
	
	public CableNetwork() {
		cables = new ArrayList<>();
	}

	public List<TileCable> getCables() {
		return cables;
	}
	
	
}
