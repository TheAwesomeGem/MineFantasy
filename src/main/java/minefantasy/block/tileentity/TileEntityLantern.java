package minefantasy.block.tileentity;

import minefantasy.block.BlockListMF;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;


/**
 *
 * @author Anonymous Productions
 * 
 * Sources are provided for educational reasons.
 * though small bits of code, or methods can be used in your own creations.
 */
public class TileEntityLantern extends TileEntity {
    
    public boolean XP;
    public boolean XM;
    public boolean ZP;
    public boolean ZM;
    public TileEntityLantern() {
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        int x = this.xCoord;
        int y = this.yCoord;
        int z = this.zCoord;
        XP = updateBlockInfo(worldObj, x+1, y, z);
        ZM = updateBlockInfo(worldObj, x, y, z+1);
        XM = updateBlockInfo(worldObj, x-1, y, z);
        ZP = updateBlockInfo(worldObj, x, y, z-1);
    }

    private boolean updateBlockInfo(World world, int x, int y, int z) {
        int id = world.getBlockId(x, y, z);
        if(id != 0)
        {
            if(Block.blocksList[id].isOpaqueCube() && id != BlockListMF.lantern.blockID)
            {
                return true;
            }
        }
        return false;
    }
}
