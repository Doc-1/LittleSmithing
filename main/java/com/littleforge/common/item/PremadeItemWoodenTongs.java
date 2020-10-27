package com.littleforge.common.item;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.util.Color;
import org.lwjgl.util.vector.Quaternion;

import com.creativemd.creativecore.client.rendering.RenderBox;
import com.creativemd.creativecore.client.rendering.model.CreativeBakedModel;
import com.creativemd.creativecore.client.rendering.model.ICreativeRendered;
import com.creativemd.creativecore.common.gui.container.SubGui;
import com.creativemd.creativecore.common.gui.controls.gui.GuiButton;
import com.creativemd.creativecore.common.packet.PacketHandler;
import com.creativemd.creativecore.common.utils.math.Rotation;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.creativemd.creativecore.common.utils.tooltip.TooltipUtils;
import com.creativemd.creativecore.common.utils.type.Pair;
import com.creativemd.littletiles.LittleTiles;
import com.creativemd.littletiles.client.gui.SubGuiChisel;
import com.creativemd.littletiles.client.gui.SubGuiMarkMode;
import com.creativemd.littletiles.client.gui.configure.SubGuiConfigure;
import com.creativemd.littletiles.client.gui.configure.SubGuiModeSelector;
import com.creativemd.littletiles.client.render.cache.ItemModelCache;
import com.creativemd.littletiles.client.render.overlay.PreviewRenderer;
import com.creativemd.littletiles.common.action.LittleAction;
import com.creativemd.littletiles.common.api.ILittleTile;
import com.creativemd.littletiles.common.block.BlockTile;
import com.creativemd.littletiles.common.container.SubContainerConfigure;
import com.creativemd.littletiles.common.item.ItemLittleGrabber;
import com.creativemd.littletiles.common.item.ItemMultiTiles;
import com.creativemd.littletiles.common.packet.LittleBlockPacket;
import com.creativemd.littletiles.common.packet.LittleBlockPacket.BlockPacketAction;
import com.creativemd.littletiles.common.packet.LittleVanillaBlockPacket;
import com.creativemd.littletiles.common.packet.LittleVanillaBlockPacket.VanillaBlockAction;
import com.creativemd.littletiles.common.structure.LittleStructure;
import com.creativemd.littletiles.common.structure.exception.CorruptedConnectionException;
import com.creativemd.littletiles.common.structure.exception.NotYetConnectedException;
import com.creativemd.littletiles.common.structure.registry.LittleStructureRegistry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructurePremadeEntry;
import com.creativemd.littletiles.common.structure.type.premade.LittleStructurePremade.LittleStructureTypePremade;
import com.creativemd.littletiles.common.tile.LittleTile;
import com.creativemd.littletiles.common.tile.LittleTileColored;
import com.creativemd.littletiles.common.tile.math.box.LittleBox;
import com.creativemd.littletiles.common.tile.math.box.LittleBoxes;
import com.creativemd.littletiles.common.tile.math.vec.LittleAbsoluteVec;
import com.creativemd.littletiles.common.tile.math.vec.LittleVec;
import com.creativemd.littletiles.common.tile.parent.IParentTileList;
import com.creativemd.littletiles.common.tile.preview.LittleAbsolutePreviews;
import com.creativemd.littletiles.common.tile.preview.LittlePreview;
import com.creativemd.littletiles.common.tile.preview.LittlePreviews;
import com.creativemd.littletiles.common.tile.registry.LittleTileRegistry;
import com.creativemd.littletiles.common.tileentity.TileEntityLittleTiles;
import com.creativemd.littletiles.common.util.grid.LittleGridContext;
import com.creativemd.littletiles.common.util.place.MarkMode;
import com.creativemd.littletiles.common.util.place.PlacementMode;
import com.creativemd.littletiles.common.util.place.PlacementPosition;
import com.creativemd.littletiles.common.util.shape.DragShape;
import com.littleforge.LittleForge;
import com.littleforge.heated.structures.DirtyIronStructurePremade;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PremadeItemWoodenTongs extends Item implements ICreativeRendered {
	
	String registryName;
	int structureTemp;
	int itemTemp;
	
	public PremadeItemWoodenTongs(String unlocalizedName, String registryNm) {
		registryName = registryNm;
		setUnlocalizedName(unlocalizedName);
		setRegistryName(registryNm);
		setCreativeTab(LittleTiles.littleTab);
		setMaxStackSize(1);
	}	

	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		NBTTagCompound nbt = new NBTTagCompound();
		stack.writeToNBT(nbt);
		if(stack.hasTagCompound()) 
			if(stack.getTagCompound().hasKey("temperature")) {
				
			}else {
			
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
		if(structure instanceof DirtyIronStructurePremade) {
			if(player.getHeldItemOffhand().getItem() instanceof PremadeItemWoodenTongs ) {
				boolean removedStructure = true;
				NBTTagCompound nbtPremade = new NBTTagCompound();
				structure.writeToNBT(nbtPremade);
				String structureID = nbtPremade.getString("id");
				try {
					structure.removeStructure();
				} catch (CorruptedConnectionException | NotYetConnectedException e) {
					removedStructure = false;
					e.printStackTrace();
				} finally {
					if(!worldIn.isRemote && removedStructure) {
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
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	}
	
	@Override
	public boolean canDestroyBlockInCreative(World world, BlockPos pos, ItemStack stack, EntityPlayer player) {
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public List<RenderBox> getRenderingCubes(IBlockState state, TileEntity te, ItemStack stack) {
		LittleStructureTypePremade premade = null;
		LittlePreviews previews = null ;
		if(stack.hasTagCompound()) {
			premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType("wooden_tongs_dirtyiron");
			previews = LittleStructurePremade.getPreviews(premade.id).copy();
		}else {
			premade = (LittleStructureTypePremade) LittleStructureRegistry.getStructureType(registryName);
			previews = LittleStructurePremade.getPreviews(premade.id).copy();
		}
		List<RenderBox> cubes = premade.getRenderingCubes(previews);
		if (cubes == null) {
			cubes = new ArrayList<>();
			
			for (LittlePreview preview : previews.allPreviews())
				cubes.add(preview.getCubeBlock(previews.getContext()));
		}
		return cubes;	
	}

	
	@SideOnly(Side.CLIENT)
	public static IBakedModel model;
	
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
