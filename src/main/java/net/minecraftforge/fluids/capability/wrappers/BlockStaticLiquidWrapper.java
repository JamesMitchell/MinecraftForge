package net.minecraftforge.fluids.capability.wrappers;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockStaticLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.capability.IFluidHandler;

/**
 * Wrapper to handle vanilla Water or Lava as an IFluidHandler.
 * Methods are modeled after {@link net.minecraft.item.ItemBucket#onItemRightClick(ItemStack, World, EntityPlayer, EnumHand)}
 */
public class BlockStaticLiquidWrapper implements IFluidHandler
{
	protected final BlockStaticLiquid blockLiquid;
	protected final World world;
	protected final BlockPos blockPos;

	public BlockStaticLiquidWrapper(BlockStaticLiquid blockLiquid, World world, BlockPos blockPos)
	{
		this.blockLiquid = blockLiquid;
		this.world = world;
		this.blockPos = blockPos;
	}

	@Override
	public FluidTankInfo[] getTankInfo()
	{
		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() == blockLiquid)
		{
			FluidStack containedStack = getStack(blockState);
			if (containedStack != null)
			{
				return new FluidTankInfo[]{new FluidTankInfo(containedStack, FluidContainerRegistry.BUCKET_VOLUME)};
			}
		}
		return new FluidTankInfo[0];
	}

	@Override
	public int fill(FluidStack resource, boolean doFill)
	{
		return 0;
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain)
	{
		if (resource == null || resource.amount < FluidContainerRegistry.BUCKET_VOLUME)
		{
			return null;
		}

		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() == blockLiquid)
		{
			FluidStack containedStack = getStack(blockState);
			if (containedStack != null && resource.containsFluid(containedStack))
			{
				if (doDrain)
				{
					world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11);
				}
				return containedStack;
			}

		}
		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain)
	{
		if (maxDrain < FluidContainerRegistry.BUCKET_VOLUME)
		{
			return null;
		}

		IBlockState blockState = world.getBlockState(blockPos);
		if (blockState.getBlock() == blockLiquid)
		{
			FluidStack containedStack = getStack(blockState);
			if (containedStack != null && containedStack.amount <= maxDrain)
			{
				if (doDrain)
				{
					world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 11);
				}
				return containedStack;
			}

		}
		return null;
	}

	@Nullable
	private FluidStack getStack(IBlockState blockState)
	{
		Block block = blockState.getBlock();
		if (block == Blocks.WATER && blockState.getValue(BlockLiquid.LEVEL) == 0)
		{
			return new FluidStack(FluidRegistry.WATER, FluidContainerRegistry.BUCKET_VOLUME);
		}
		else if (block == Blocks.LAVA && blockState.getValue(BlockLiquid.LEVEL) == 0)
		{
			return new FluidStack(FluidRegistry.LAVA, FluidContainerRegistry.BUCKET_VOLUME);
		}
		else
		{
			return null;
		}
	}
}