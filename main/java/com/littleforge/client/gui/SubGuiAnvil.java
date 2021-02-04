package com.littleforge.client.gui;

import java.util.ArrayList;
import java.util.List;

import com.creativemd.creativecore.common.gui.GuiRenderHelper;
import com.creativemd.creativecore.common.gui.client.style.ColoredDisplayStyle;
import com.creativemd.creativecore.common.gui.client.style.DisplayStyle;
import com.creativemd.creativecore.common.gui.client.style.Style;
import com.creativemd.creativecore.common.gui.container.GuiParent;
import com.creativemd.creativecore.common.gui.container.SubGui;
import com.creativemd.creativecore.common.gui.controls.container.SlotControlNoSync;
import com.creativemd.creativecore.common.gui.controls.container.client.GuiSlotControl;
import com.creativemd.creativecore.common.gui.controls.gui.GuiScrollBox;
import com.creativemd.creativecore.common.gui.controls.gui.GuiTextBox;
import com.creativemd.creativecore.common.utils.mc.ColorUtils;
import com.littleforge.LittleForge;
import com.littleforge.common.recipe.forge.LittleAnvilRecipe.MetalTemperature;
import com.littleforge.common.strucutres.type.premade.interactive.InteractiveAnvilPremade;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class SubGuiAnvil extends SubGui {
	
	private static final DisplayStyle SELECTED_DISPLAY = new ColoredDisplayStyle(ColorUtils.YELLOW);
	private InteractiveAnvilPremade anvil;
	
	public SubGuiAnvil(InteractiveAnvilPremade anvil) {
		super(260, 180);
		this.anvil = anvil;
		
	}
	
	@Override
	public void createControls() {
		List<String> lines = new ArrayList<String>();
		lines.add("Da");
		GuiScrollBox selector = new GuiScrollBox("preview", 0, 20, 250, 75);
		GuiTextBox temperatureText = new GuiTextBox("temperature", "Temperature", 0, 0, 80);
		GuiTextBox hitText = new GuiTextBox("hit", "Hits", 71, 0, 80);
		GuiTextBox ingredientText = new GuiTextBox("ingredient", "Ingredients", 116, 0, 80);
		GuiTextBox resultText = new GuiTextBox("result", "Result", 210, 0, 80);
		temperatureText.setStyle(Style.emptyStyle);
		hitText.setStyle(Style.emptyStyle);
		ingredientText.setStyle(Style.emptyStyle);
		resultText.setStyle(Style.emptyStyle);
		selector.controls.add(temperatureText);
		selector.controls.add(ingredientText);
		selector.controls.add(resultText);
		selector.controls.add(hitText);
		selector.controls.add(new GuiAnvilRecipeSelector("test", MetalTemperature.RED, 0, (1 * 28) - 15, 250, 20, 0, 4, 20));
		selector.controls.add(new GuiAnvilRecipeSelector("test1", MetalTemperature.BRIGHT_RED, 0, (2 * 28) - 15, 250, 20, 1, 5, 35));
		selector.controls.add(new GuiAnvilRecipeSelector("test2", MetalTemperature.BRIGHT_ORANGE, 0, (3 * 28) - 15, 250, 20, 2, 2, 15));
		selector.controls.add(new GuiAnvilRecipeSelector("test3", MetalTemperature.YELLOW, 0, (4 * 28) - 15, 250, 20, 3, 3, 5));
		
		controls.add(selector);
		
	}
	
	public class GuiAnvilRecipeSelector extends GuiParent {
		
		public boolean selected = false;
		private MetalTemperature tempColor;
		private int hits;
		public final int index;
		
		public GuiAnvilRecipeSelector(String name, MetalTemperature tempColor, int x, int y, int width, int height, int index, int ingerdients, int hits) {
			super(name, x, y, width, height);
			this.tempColor = tempColor;
			this.hits = hits;
			this.index = index;
			InventoryBasic basic = new InventoryBasic("", false, ingerdients);
			int counter = 0;
			for (int i = 0; i < ingerdients; i++) {
				int col = i % 5;
				int row = i / 5;
				basic.setInventorySlotContents(i, new ItemStack(Items.IRON_INGOT, 1));
				addControl(new GuiSlotControlSelect(this, col, row, index, basic));
				counter++;
			}
			
		}
		
		public void select(int index) {
			
			this.selected = true;
			/*
			for (int i = 0; i < controls.size(); i++) {
				GuiSlotControlSelect slot = (GuiSlotControlSelect) controls.get(i);
				slot.selected = index == i;
			}
			raiseEvent(new GuiControlChangedEvent(this));
			*/
		}
		
		@Override
		public boolean mousePressed(int posX, int posY, int button) {
			System.out.println(index);
			selected = true;
			playSound(SoundEvents.UI_BUTTON_CLICK);
			return super.mousePressed(posX, posY, button);
		}
		
		@Override
		public DisplayStyle getBorderDisplay(DisplayStyle display) {
			if (selected)
				return SELECTED_DISPLAY;
			return super.getBorderDisplay(display);
		}
		
		private void renderTemperature(GuiRenderHelper helper, int width, int height) {
			int color = tempColor.getColor();
			int tempMin = tempColor.getTemperatureMin();
			int y1 = 1;
			int x1 = 11;
			int width1 = 40;
			helper.drawHorizontalChannelMaskGradientRect(x1, y1, x1 + width1, y1 + 16, color, 60);
			helper.drawRect(x1, y1, x1 + 1, y1 + 16, 0xff373737);
			helper.drawRect(x1, y1, x1 + width1, y1 + 1, 0xff373737);
			helper.drawRect(x1 + width1 - 1, y1, x1 + width1, y1 + 1, 0xff8b8b8b);
			helper.drawRect(x1 + width1 - 1, y1 + 1, x1 + width1, y1 + 16, 0xffffffff);
			helper.drawRect(x1 + 1, y1 + 15, x1 + width1, y1 + 16, 0xffffffff);
			helper.drawRect(x1, y1 + 15, x1 + 1, y1 + 16, 0xff8b8b8b);
			helper.drawStringWithShadow(String.valueOf(tempMin) + "°C", x1, y1, x1 * 2 + 34, y1 + 17, ColorUtils.WHITE, 5);
		}
		
		private void renderHits(GuiRenderHelper helper, int width, int height) {
			int y1 = 1;
			int x1 = 69;
			int width1 = 21;
			helper.drawRect(x1, y1, x1 + width1, y1 + 16, 0xff8b8b8b);
			helper.drawRect(x1, y1, x1 + 1, y1 + 16, 0xff373737);
			helper.drawRect(x1, y1, x1 + width1, y1 + 1, 0xff373737);
			helper.drawRect(x1 + width1 - 1, y1, x1 + width1, y1 + 1, 0xff8b8b8b);
			helper.drawRect(x1 + width1 - 1, y1 + 1, x1 + width1, y1 + 16, 0xffffffff);
			helper.drawRect(x1 + 1, y1 + 15, x1 + width1, y1 + 16, 0xffffffff);
			helper.drawRect(x1, y1 + 15, x1 + 1, y1 + 16, 0xff8b8b8b);
			helper.drawStringWithShadow(String.valueOf(hits), x1, y1, x1 * 2 + 15, y1 + 17, ColorUtils.WHITE, 5);
		}
		
		@Override
		protected void renderContent(GuiRenderHelper helper, Style style, int width, int height) {
			renderTemperature(helper, width, height);
			renderHits(helper, width, height);
			GlStateManager.scale(0.065, 0.06, 0);
			helper.drawTexturedModalRect(new ResourceLocation(LittleForge.MODID, "textures/gui/arrow.png"), 187 * 16, 1 * 16, 0, 0, 16 * 16, 16 * 16);
			
		}
	}
	
	public class GuiSlotControlSelect extends GuiSlotControl {
		
		public final GuiAnvilRecipeSelector selector;
		public final int index;
		
		public GuiSlotControlSelect(GuiAnvilRecipeSelector selector, int col, int row, int index, InventoryBasic basic) {
			super(100 + (col * 18), row * 18, new SlotControlNoSync(new Slot(basic, index, col * 18, row * 18)));
			
			this.selector = selector;
			this.index = index;
			
		}
		
		@Override
		public boolean hasMouseOverEffect() {
			return false;
		}
		
	}
}
