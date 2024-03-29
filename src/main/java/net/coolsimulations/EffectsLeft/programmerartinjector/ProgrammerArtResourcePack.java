package net.coolsimulations.EffectsLeft.programmerartinjector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.regex.Pattern;

import com.google.gson.JsonObject;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.FolderPackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.VanillaPackResources;
import net.minecraft.server.packs.metadata.MetadataSectionSerializer;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.metadata.pack.PackMetadataSectionSerializer;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.IModFileInfo;

/***
 * 
 * @author ExtraCraftTX
 * @see https://github.com/ExtraCrafTX/ProgrammerArtInjector
 *
 */
public class ProgrammerArtResourcePack extends FilePackResources {

	private static final Pattern RESOURCE_PACK_PATH = Pattern.compile("[a-z0-9-_]+");

    public ProgrammerArtResourcePack(File file) {
        super(file);
    }

    @Override
    public String getName() {
        return "Programmer Art";
    }

    @Override
    public boolean hasResource(PackType resourceType, ResourceLocation resource) {
        boolean contains = super.hasResource(resourceType, resource);
        if(contains || resourceType.equals(PackType.SERVER_DATA))
            return contains;
        String path = String.format("programmer_art/%s/%s/%s", resourceType.getDirectory(), resource.getNamespace(), resource.getPath());
        try{
            Enumeration<URL> urls = VanillaPackResources.class.getClassLoader().getResources(path);
            URL url = null;

            while(urls.hasMoreElements())
                url = urls.nextElement();

            return isValid(path, url);
        }catch(IOException e){
            return false;
        }
    }

    private static boolean isValid(String path, URL url) throws IOException {
        return url != null && (url.getProtocol().equals("jar") || FolderPackResources.validatePath(new File(url.getFile()), "/"+path));
    }

    @Override
    public InputStream getResource(PackType resourceType, ResourceLocation resource) throws IOException {
        if(super.hasResource(resourceType, resource))
            return super.getResource(resourceType, resource);
        String path = String.format("programmer_art/%s/%s/%s", resourceType.getDirectory(), resource.getNamespace(), resource.getPath());
        try{
            Enumeration<URL> urls = VanillaPackResources.class.getClassLoader().getResources(path);
            URL url = null;

            while(urls.hasMoreElements()){
                url = urls.nextElement();
            }

            if(isValid(path, url))
                return url.openStream();
        }catch(IOException e){

        }
        throw new FileNotFoundException(resource.getPath());
    }

    @Override
    public Set<String> getNamespaces(PackType resourceType) {
        Set<String> namespaces = super.getNamespaces(resourceType);

        if(!resourceType.equals(PackType.CLIENT_RESOURCES))
            return namespaces;

        for(IModFileInfo container : ModList.get().getModFiles()){
            Path path = container.getFile().getFilePath();
            path = path.toAbsolutePath().normalize();
            
            Path childPath = container.getFile().findResource("/programmer_art").resolve(("/"+resourceType.getDirectory()).replace("/", path.getFileSystem().getSeparator())).toAbsolutePath().normalize();
            if(Files.exists(childPath)){
                try(DirectoryStream<Path> dirs = Files.newDirectoryStream(childPath, Files::isDirectory)){
                    for(Path dir : dirs){
                        String name = dir.getFileName().toString();
                        name = name.replace(path.getFileSystem().getSeparator(), "");

                        if(RESOURCE_PACK_PATH.matcher(name).matches()){
                            namespaces.add(name);
                        }else{
                            System.out.println("Ignoring invalid namespace " + name + " in mod " + container.getFile().getFileName());
                        }
                    }
                }catch(IOException e){

                }
            }
        }

        return namespaces;
    }

    @Override
    protected InputStream getResource(String name) throws IOException {
        if(name.equals("pack.png")){
        	try {
        		return VanillaPackResources.class.getClassLoader().getResources("programmer_art/"+name).nextElement().openStream();
        	} catch (NoSuchElementException e) {
        		return super.getResource(name);
        	}
        }
        return super.getResource(name);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getMetadataSection(MetadataSectionSerializer<T> metaReader) throws IOException {
        if(metaReader instanceof PackMetadataSectionSerializer)
            return super.getMetadataSection((MetadataSectionSerializer<T>) READER);
        return super.getMetadataSection(metaReader);
    }

    private static final String DESCRIPTION = "The classic look of Minecraft,\n§8§onow with some mods!";
    private static final CustomMetadataReader READER = new CustomMetadataReader(DESCRIPTION);

    private static class CustomMetadataReader implements MetadataSectionSerializer<PackMetadataSection>{

        private final Component text;

        public CustomMetadataReader(String description) {
            this.text = new TextComponent(description);
        }
        
        public PackMetadataSection fromJson(JsonObject jsonObject) {
            int pack_format = GsonHelper.getAsInt(jsonObject, "pack_format");
            return new PackMetadataSection(text, pack_format);
        }

        public String getMetadataSectionName() {
            return "pack";
        }
    }

}