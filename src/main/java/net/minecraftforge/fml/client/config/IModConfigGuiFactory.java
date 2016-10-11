/*
 * Minecraft Forge
 * Copyright (c) 2016.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 2.1
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package net.minecraftforge.fml.client.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.common.Mod;

/**
 * Factory class to create mod config guis.
 * Register yours with {@link Mod#configGuiFactory()}.
 * Implementations must have a no-argument constructor.
 *
 * Replaces {@link net.minecraftforge.fml.client.IModGuiFactory}.
 */
public interface IModConfigGuiFactory
{
	/**
	 * Creates a new config gui screen for this mod for when its config button is pressed on the mod list screen.
	 *
	 * See {@link GuiConfig} for a standard implementation you can use.
	 * This screen will replace the "mod list" screen, and return to the mod list screen (parent) once the
	 * appropriate action is taken from the config screen ("Done" button).
	 *
	 * @param parent the mod list screen
	 * @return the GuiScreen to be shown when the "config" button is pressed in the mod list.
	 */
	GuiScreen createConfigGui(GuiScreen parent);
}
