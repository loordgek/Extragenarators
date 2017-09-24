package loordgek.extragenarators.client;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * A hackish adapter that allows lambdas to be used as {@link ItemMeshDefinition} implementations without breaking ForgeGradle's
 * reobfuscation and causing {@link AbstractMethodError}s.
 * <p>
 * Written by diesieben07 in this thread:
 * http://www.minecraftforge.net/forum/index.php/topic,34034.0.html
 *
 * @author diesieben07
 */
@FunctionalInterface
interface MeshDefinitionFix extends ItemMeshDefinition {
	ModelResourceLocation getLocation(final ItemStack stack);

	// Helper method to easily create lambda instances of this class
	static ItemMeshDefinition create(final MeshDefinitionFix lambda) {
		return lambda;
	}

	@Override
	@Nonnull
	default ModelResourceLocation getModelLocation(final @Nonnull ItemStack stack) {
		return getLocation(stack);
	}
}
