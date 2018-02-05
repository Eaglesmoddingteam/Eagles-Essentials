package btf.objects.worldDataSaving;


import btf.main.Vars;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.storage.WorldSavedData;

public class Seasons extends WorldSavedData implements ITickable {
	 private static final String DATA_NAME = Vars.MOD_ID + ":" + "Seasons";
	 private int[] time = new int[3];
	  // Required constructors
	  public Seasons() {
	    super(DATA_NAME);
	  }
	  public Seasons(String s) {
	    super(s);
	  }
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		return null;
	}
	
	int ticksInCurrentDay= 0;
	long totalticks = 0;
	private int currentday = 0;
	private int currentseason= 0;
	private int currentyear = 0;
	@Override
	public void update() {
		ticksInCurrentDay++;
		if(ticksInCurrentDay == 24000) {
			totalticks += ticksInCurrentDay;
			ticksInCurrentDay = 0;
			currentday = (int) (totalticks / 24000);
			currentseason = (int) (totalticks / (24000 * 10));
			currentyear = (int) (totalticks / (24000 * 120));
			time[0]=currentday;
			time[1]=currentseason;
			time[2]=currentyear;
			}
	}
	public int getDay() {
		
		return time[0];
	}
	
	public int getSeason() {
		
		return time[2];
	}
	
	public int getYear() {
		
		return time[3];
	}
	
	public void forceNextday() {
		ticksInCurrentDay = 0;
		totalticks += 24000;
	}
}
