package com.the9grounds.aeadditions.registries

import com.the9grounds.aeadditions.AEAdditions
import com.the9grounds.aeadditions.block.CraftingStorageBlock
import com.the9grounds.aeadditions.block.crafting.ExtendedCraftingUnitType
import com.the9grounds.aeadditions.core.BlockDefinition
import com.the9grounds.aeadditions.integration.Mods
import net.minecraft.resources.ResourceLocation
import net.minecraft.world.item.BlockItem
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockBehaviour
import net.minecraft.world.level.material.Material
import net.minecraftforge.registries.ForgeRegistries
import thedarkcolour.kotlinforforge.forge.KDeferredRegister
import thedarkcolour.kotlinforforge.forge.registerObject

object Blocks {
    val REGISTRY = KDeferredRegister.create(ForgeRegistries.BLOCKS, AEAdditions.ID)

    val BLOCKS = mutableListOf<Block>()
    
    
    @JvmField val BLOCK_CRAFTING_STORAGE_1024k = createBlock(Ids.CRAFTING_STORAGE_1024k, Material.METAL) {
        CraftingStorageBlock(it, ExtendedCraftingUnitType.STORAGE_1024)
    }
    @JvmField val BLOCK_CRAFTING_STORAGE_4096k = createBlock(Ids.CRAFTING_STORAGE_4096k, Material.METAL) {
        CraftingStorageBlock(it, ExtendedCraftingUnitType.STORAGE_4096)
    }
    @JvmField val BLOCK_CRAFTING_STORAGE_16384k = createBlock(Ids.CRAFTING_STORAGE_16384k, Material.METAL) {
        CraftingStorageBlock(it, ExtendedCraftingUnitType.STORAGE_16384)
    }
    @JvmField val BLOCK_CRAFTING_STORAGE_65536k = createBlock(Ids.CRAFTING_STORAGE_65536k, Material.METAL) {
        CraftingStorageBlock(it, ExtendedCraftingUnitType.STORAGE_65536)
    }
    
    fun init() {
        
    }

    fun <T: Block> createBlock(id: ResourceLocation, material: Material, factory: (BlockBehaviour.Properties) -> T): BlockDefinition<T> {
        val block = constructBlock(material, factory, id)

        val item = Items.createItem(id) { properties -> BlockItem(block, properties) }

        return BlockDefinition(block, item)
    }

    fun <T: Block> createBlock(id: ResourceLocation, material: Material, requiredMod: Mods, factory: (BlockBehaviour.Properties) -> T): BlockDefinition<T> {
        val block = constructBlock(material, factory, id)

        val item = Items.createItem(id, { properties -> BlockItem(block, properties) }, requiredMod)

        return BlockDefinition(block, item)
    }

    private fun <T : Block> constructBlock(
        material: Material,
        factory: (BlockBehaviour.Properties) -> T,
        id: ResourceLocation
    ): T {
        val props = BlockBehaviour.Properties.of(material)

        val block = factory(props)

        BLOCKS.add(block)

        REGISTRY.registerObject(id.path) {
            block
        }
        return block
    }
}