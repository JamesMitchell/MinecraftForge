package net.minecraftforge.debug;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static net.minecraftforge.fluids.capability.templates.FluidHandlerItemStack.FLUID_NBT_KEY;

@Mod(modid = FluidPlacementTest.MODID, name = "ForgeDebugFluidPlacement", version = FluidPlacementTest.VERSION)
public class FluidPlacementTest
{
    public static final String MODID = "forgedebugfluidplacement";
    public static final String VERSION = "1.0";

    public static final boolean ENABLE = true;

    private static Item emptyContainer;

    @SidedProxy
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        if (ENABLE && ModelFluidDebug.ENABLE)
        {
            proxy.preInit(event);
        }
    }

    public static class CommonProxy
    {
        public void preInit(FMLPreInitializationEvent event)
        {
            emptyContainer = new ItemBucket(Blocks.AIR)
                .setRegistryName(MODID, "empty_fluid_container")
                .setUnlocalizedName(MODID + ":empty_fluid_container")
                .setMaxStackSize(16);
            FluidRegistry.registerFluid(FiniteFluid.instance);
            FluidRegistry.addBucketForFluid(FiniteFluid.instance);
            GameRegistry.register(emptyContainer);
            GameRegistry.register(FluidContainer.instance);
            GameRegistry.register(FiniteFluidBlock.instance);
            GameRegistry.register(new ItemBlock(FiniteFluidBlock.instance).setRegistryName(FiniteFluidBlock.instance.getRegistryName()));
        }
    }

    public static class ServerProxy extends CommonProxy
    {
    }

    public static class ClientProxy extends CommonProxy
    {
        private static ModelResourceLocation fluidLocation = new ModelResourceLocation(MODID.toLowerCase() + ":" + FiniteFluidBlock.name, "normal");

        @Override
        public void preInit(FMLPreInitializationEvent event)
        {
            super.preInit(event);
            Item fluid = Item.getItemFromBlock(FiniteFluidBlock.instance);
            ModelLoader.setCustomModelResourceLocation(emptyContainer, 0, new ModelResourceLocation("forge:bucket", "inventory"));
            ModelLoader.setBucketModelDefinition(FluidContainer.instance);
            // no need to pass the locations here, since they'll be loaded by the block model logic.
            ModelBakery.registerItemVariants(fluid);
            ModelLoader.setCustomMeshDefinition(fluid, new ItemMeshDefinition()
            {
                public ModelResourceLocation getModelLocation(ItemStack stack)
                {
                    return fluidLocation;
                }
            });
            ModelLoader.setCustomStateMapper(FiniteFluidBlock.instance, new StateMapperBase()
            {
                protected ModelResourceLocation getModelResourceLocation(IBlockState state)
                {
                    return fluidLocation;
                }
            });
        }
    }

    public static final class FiniteFluid extends Fluid
    {
        public static final String name = "finitefluid";
        public static final FiniteFluid instance = new FiniteFluid();

        private FiniteFluid()
        {
            super(name, new ResourceLocation("blocks/water_still"), new ResourceLocation("blocks/water_flow"));
        }

        @Override
        public int getColor()
        {
            return 0xFFFFFF00;
        }

        @Override
        public String getLocalizedName(FluidStack stack)
        {
            return "Finite Fluid";
        }
    }

    public static final class FiniteFluidBlock extends BlockFluidFinite
    {
        public static final FiniteFluidBlock instance = new FiniteFluidBlock();
        public static final String name = "finite_fluid_block";

        private FiniteFluidBlock()
        {
            super(FiniteFluid.instance, Material.WATER);
            setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
            setUnlocalizedName(MODID + ":" + name);
            setRegistryName(MODID, name);
        }
    }

    public static final class FluidContainer extends UniversalBucket
    {
        public static final FluidContainer instance = new FluidContainer();
        public static final String name = "fluid_container";

        private FluidContainer()
        {
            super(1000, new ItemStack(emptyContainer), false);
            setCreativeTab(CreativeTabs.MISC);
            setRegistryName(MODID, name);
            setUnlocalizedName(MODID + ":" + name);
        }

        @Nonnull
        @Override
        public String getItemStackDisplayName(@Nonnull ItemStack stack)
        {
            FluidStack fluid = getFluid(stack);
            if (fluid == null)
            {
                return "Empty Variable Container";
            }
            return "Variable Container (" + getFluid(stack).getLocalizedName() + ")";
        }

        @Override
        public FluidStack getFluid(ItemStack container)
        {
            container = container.copy();
            if (container.getTagCompound() != null)
                container.setTagCompound(container.getTagCompound().getCompoundTag(FLUID_NBT_KEY));
            return super.getFluid(container);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced)
        {
            tooltip.add(getFluid(stack).amount + "/1000");
        }

        @Override
        public void getSubItems(@Nonnull Item itemIn, @Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> subItems)
        {
            Fluid[] fluids = new Fluid[]{FluidRegistry.WATER, FluidRegistry.LAVA, FiniteFluid.instance, ModelFluidDebug.TestFluid.instance};
            // add 16 variable fillings
            for (Fluid fluid : fluids)
            {
                for (int amount = 125; amount <= 1000; amount += 125)
                {
                    for (int offset = 63; offset >= 0; offset -= 63)
                    {
                        FluidStack fs = new FluidStack(fluid, amount - offset);
                        ItemStack stack = new ItemStack(this);
                        NBTTagCompound tag = stack.getTagCompound();
                        if (tag == null)
                        {
                            tag = new NBTTagCompound();
                        }
                        NBTTagCompound fluidTag = new NBTTagCompound();
                        fs.writeToNBT(fluidTag);
                        tag.setTag(FLUID_NBT_KEY, fluidTag);
                        stack.setTagCompound(tag);
                        subItems.add(stack);
                    }
                }
            }
        }

        @Override
        public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound nbt)
        {
            return new FluidHandlerItemStack.SwapEmpty(stack, getEmpty(), 1000);
        }
    }
}