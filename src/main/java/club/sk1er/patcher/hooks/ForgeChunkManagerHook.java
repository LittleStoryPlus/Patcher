package club.sk1er.patcher.hooks;

//#if MC==10809
import club.sk1er.patcher.asm.external.forge.ForgeChunkManagerTransformer;
import com.google.common.collect.ImmutableSetMultimap;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import org.objectweb.asm.tree.ClassNode;

import java.util.Map;

/**
 * Used in {@link ForgeChunkManagerTransformer#transform(ClassNode, String)}
 */
@SuppressWarnings("unused")
public class ForgeChunkManagerHook {

    public static ImmutableSetMultimap<ChunkCoordIntPair, Ticket> getPersistentChunksFor(World world, Map<World, ImmutableSetMultimap<ChunkCoordIntPair, Ticket>> forcedChunks) {
        if (world.isRemote) {
            return ImmutableSetMultimap.of();
        }

        ImmutableSetMultimap<ChunkCoordIntPair, Ticket> persistentChunks = forcedChunks.get(world);
        return persistentChunks != null ? persistentChunks : ImmutableSetMultimap.of();
    }
}
//#endif
