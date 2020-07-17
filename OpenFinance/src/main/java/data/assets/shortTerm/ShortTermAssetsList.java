package data.assets.shortTerm;

import java.util.ArrayList;

public class ShortTermAssetsList {

	private ArrayList<ShortTermAsset> shortTermAssets;
	
	public ShortTermAssetsList() {
		shortTermAssets = new ArrayList<ShortTermAsset>();
	}
	
	public ArrayList<ShortTermAsset> returnShortTermAssets(){
		return shortTermAssets;
	}
	
	public void addShortTermAsset(ShortTermAsset asset) {
		shortTermAssets.add(asset);
	}
	
	public void removeShortTermAsset(ShortTermAsset asset) {
		for(int i = 0; i < shortTermAssets.size(); i++) {
			if(asset.equals(shortTermAssets.get(i))) {
				shortTermAssets.remove(i);
			}
		}
	}
	
}
