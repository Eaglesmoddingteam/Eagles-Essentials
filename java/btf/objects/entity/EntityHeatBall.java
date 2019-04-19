package btf.objects.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class EntityHeatBall extends Entity {

	public static AxisAlignedBB AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 0.2, 0.2, 0.2);
	EntityPlayer player = null;

	public EntityHeatBall(World world) {
		super(world);
	}

	public EntityHeatBall(EntityPlayer player) {

		this(player.getEntityWorld());
		Vec3d vec3d = player.getPositionEyes(1f);
		posX = vec3d.x;
		posY = vec3d.y + 0.1;
		posZ = vec3d.z;
		this.player = player;

		float pitch = player.rotationPitch;
		float yaw = player.rotationYaw;

		float x = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		float y = -MathHelper.sin(pitch * 0.017453292F);
		float z = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		this.motionX += player.motionX;
		this.motionZ += player.motionZ;
		setSize(0.2f, 0.2f);

		EntityZombie zombie = new EntityZombie(world);
		zombie.startRiding(this);
		this.addPassenger(zombie);

		if (!player.onGround) {
			this.motionY += player.motionY;
		}

	}

	@Override
	protected void entityInit() {
		// shouldn't really do anything
	}

	@Override
	public void onUpdate() {
		ticksExisted++;
		this.posX += motionX;
		this.posY += motionY;
		this.posZ += motionZ;
		doBlockCollisions();
		checkEntityCollisions();
	}

	private void checkEntityCollisions() {
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(
				lastTickPosX, lastTickPosY, lastTickPosZ, posX + motionX, posY + motionY, posZ + motionZ));
		if (entities.isEmpty() || ticksExisted < 3) {
			return;
		}
		for (Entity e : entities) {
			e.setFire(10);
		}
		setDead();
	}

	/**
	 * Checks for blocks colliding with the entity, and if they do so, they'll get
	 * broken
	 */
	@Override
	protected void doBlockCollisions() {
		BlockPos pos = new BlockPos(this);
		if (world.isBlockLoaded(pos) && !world.isRemote) {
			AxisAlignedBB aabb$block = world.getBlockState(pos).getCollisionBoundingBox(world, pos);
			if (aabb$block != null && AABB.intersects(aabb$block)) {
				Block block = world.getBlockState(pos).getBlock();
				if (block.canHarvestBlock(world, pos, player)) {
					NonNullList<ItemStack> drops = NonNullList.create();
					block.getDrops(drops, world, pos, world.getBlockState(pos), 8);
					for (ItemStack stack : drops) {
						InventoryHelper.spawnItemStack(world, posX, posY, posZ, stack);
					}
					world.setBlockToAir(pos);
					this.setDead();
				}
			}
		}
	}

	@Override
	public AxisAlignedBB getEntityBoundingBox() {
		return AABB;
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return null;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		posX = compound.getDouble("x");
		posY = compound.getDouble("y");
		posZ = compound.getDouble("z");
		motionX = compound.getDouble("motionX");
		motionY = compound.getDouble("motionY");
		motionY = compound.getDouble("motionY");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setDouble("x", posX);
		compound.setDouble("y", posY);
		compound.setDouble("z", posZ);
		compound.setDouble("motionX", motionX);
		compound.setDouble("motioY", motionY);
		compound.setDouble("motionZ", motionZ);
	}

}