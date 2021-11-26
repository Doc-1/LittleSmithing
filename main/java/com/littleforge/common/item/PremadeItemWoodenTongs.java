package com.littleforge.common.item;

import java.util.List;

import javax.annotation.Nullable;

import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.common.block.BlockTile;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.littleforge.common.structures.type.premade.heatable.DirtyIronStructurePremade;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemWoodenTongs extends PremadeItem implements ICreativeRendered {
    
    String registryName;
    int structureTemp;
    int itemTemp;
    
    public PremadeItemWoodenTongs(String unlocalizedName, String registryNm) {
        super(unlocalizedName, registryNm);
        setCreativeTab(LittleTiles.littleTab);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        NBTTagCompound nbt = new NBTTagCompound();
        stack.writeToNBT(nbt);
        if (stack.hasTagCompound())
            if (stack.getTagCompound().hasKey("temperature")) {
                
            } else {
                
            }
    }
    
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        LittleStructure structure = null;
        
        TileEntityLittleTiles te = BlockTile.loadTe(worldIn, pos);
        
        if (te != null) {
            try {
                Vec3d pos1 = player.getPositionEyes(Minecraft.getMinecraft().getRenderPartialTicks());
                double d0 = player.capabilities.isCreativeMode ? 5.0F : 4.5F;
                Vec3d look = player.getLook(Minecraft.getMinecraft().getRenderPartialTicks());
                Vec3d vec32 = pos1.addVector(look.x * d0, look.y * d0, look.z * d0);
                
                structure = te.getFocusedTile(pos1, vec32).key.getStructure();
            } catch (CorruptedConnectionException | NotYetConnectedException e) {
                e.printStackTrace();
            }
        }
        if (structure instanceof DirtyIronStructurePremade) {
            if (player.getHeldItemOffhand().getItem() instanceof PremadeItemWoodenTongs) {
                boolean removedStructure = true;
                NBTTagCompound nbtPremade = new NBTTagCompound();
                structure.writeToNBT(nbtPremade);
                String structureID = nbtPremade.getString("id");
                try {
                    structure.onLittleTileDestroy();
                } catch (CorruptedConnectionException | NotYetConnectedException e) {
                    removedStructure = false;
                    e.printStackTrace();
                } finally {
                    if (!worldIn.isRemote && removedStructure) {
                        NBTTagCompound nbtOffHand = new NBTTagCompound();
                        nbtOffHand.setString("id", structureID);
                        player.getHeldItemOffhand().setTagCompound(nbtOffHand);
                        
                    }
                }
            }
        }
        return EnumActionResult.SUCCESS;
    }
    
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 0F;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {}
    
    @Override
    public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
        return false;
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void applyCustomOpenGLHackery(ItemStack stack, TransformType cameraTransformType) {
        Minecraft mc = Minecraft.getMinecraft();
        
        if (cameraTransformType == TransformType.FIRST_PERSON_RIGHT_HAND) {
            GlStateManager.translate(-0.07D, -0.07D, -0.07D);
            
            GlStateManager.rotate(-140.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(15.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(10.0F, 0.0F, 0.0F, 1.0F);
            
            //GlStateManager.scale(1.0D, 1.0D, 1.0D);
        }
        
        if (cameraTransformType == TransformType.FIRST_PERSON_LEFT_HAND) {
            GlStateManager.scale(1.4D, 1.4D, 1.4D);
            
            GlStateManager.translate(0.01D, -0.15D, -0.20D);
            GlStateManager.rotate(-30.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(-100.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
            
        }
        
        if (cameraTransformType == TransformType.GUI) {
            GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(6.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.rotate(5.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-1.3D, -14.0D, -0.02D);
            GlStateManager.scale(1.4D, 1.4D, 1.4D);
        }
        
    }
}
