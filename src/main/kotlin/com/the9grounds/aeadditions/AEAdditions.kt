package com.the9grounds.aeadditions

import appeng.api.stacks.AEKeyType
import appeng.items.storage.BasicStorageCell
import com.the9grounds.aeadditions.data.AEAdditionsDataGenerator
import com.the9grounds.aeadditions.integration.Integration
import com.the9grounds.aeadditions.integration.Mods
import com.the9grounds.aeadditions.integration.appeng.AppEng
import com.the9grounds.aeadditions.item.storage.DiskCell
import com.the9grounds.aeadditions.item.storage.StorageCell
import com.the9grounds.aeadditions.registries.Blocks
import com.the9grounds.aeadditions.registries.Cells
import com.the9grounds.aeadditions.registries.Items
import com.the9grounds.aeadditions.registries.client.Models
import io.github.projectet.ae2things.item.DISKDrive
import me.ramidzkh.mekae2.ae2.MekanismKeyType
import net.minecraft.client.renderer.ItemBlockRenderTypes
import net.minecraft.client.renderer.RenderType
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.client.event.ColorHandlerEvent
import net.minecraftforge.client.event.ModelRegistryEvent
import net.minecraftforge.fml.DistExecutor
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS

@Mod(AEAdditions.ID)
object AEAdditions {

    const val ID = "ae2additions"

    init {
        Items.REGISTRY.register(MOD_BUS)
        Blocks.REGISTRY.register(MOD_BUS)
        Items.init()
        Blocks.init()
        
        MOD_BUS.addListener(::commonSetup)
        MOD_BUS.addListener(AEAdditionsDataGenerator::onGatherData)

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT) { Runnable { initClient() } }

        Integration.init()
    }
    
    fun initClient() {
        MOD_BUS.addListener(::modelRegistryEvent)
        MOD_BUS.addListener(::registerItemColors)
    }

    private fun commonSetup(event: FMLCommonSetupEvent) {
        AppEng.initCellHandler()
        Cells.init()
        
        if (Mods.MEGAAE2.isEnabled) {
            for (item in listOf(
                Items.CELL_COMPONENT_1024k,
                Items.CELL_COMPONENT_4096k,
                Items.CELL_COMPONENT_16384k,
                Items.CELL_COMPONENT_65536k,
                Items.ITEM_STORAGE_CELL_1024k,
                Items.ITEM_STORAGE_CELL_4096k,
                Items.ITEM_STORAGE_CELL_16384k,
                Items.ITEM_STORAGE_CELL_65536k,
                Items.FLUID_STORAGE_CELL_1024k,
                Items.FLUID_STORAGE_CELL_4096k,
                Items.FLUID_STORAGE_CELL_16384k,
                Items.CHEMICAL_STORAGE_CELL_1024k,
                Items.CHEMICAL_STORAGE_CELL_4096k,
                Items.CHEMICAL_STORAGE_CELL_16384k,
            )) {
                item.category = null;
            }
        }
    }
    
    private fun registerItemColors(event: ColorHandlerEvent.Item) {
        val itemColors = event.itemColors
        
        val storageCells = Items.ITEMS.filter { it is StorageCell }.toTypedArray()
        
        itemColors.register(BasicStorageCell::getColor, *storageCells)
        
        if (Mods.AE2THINGS.isEnabled) {
            val diskCells = Items.ITEMS.filter { it is DiskCell && it.keyType == AEKeyType.fluids() }.toTypedArray()
            itemColors.register(DISKDrive::getColor, *diskCells)

            if (Mods.APPMEK.isEnabled) {
                val chemicalDiskCells = Items.ITEMS.filter { it is DiskCell && it.keyType == MekanismKeyType.TYPE }.toTypedArray()
                itemColors.register(DISKDrive::getColor, *chemicalDiskCells)
            }
        }
    }

    private fun modelRegistryEvent(event: ModelRegistryEvent) {
        Models.init()
        ItemBlockRenderTypes.setRenderLayer(Blocks.BLOCK_CRAFTING_STORAGE_1024k.block, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(Blocks.BLOCK_CRAFTING_STORAGE_4096k.block, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(Blocks.BLOCK_CRAFTING_STORAGE_16384k.block, RenderType.cutout())
        ItemBlockRenderTypes.setRenderLayer(Blocks.BLOCK_CRAFTING_STORAGE_65536k.block, RenderType.cutout())
    }
}