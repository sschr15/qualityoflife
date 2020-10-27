# QualityOfLife

QualityOfLife (QOL) is a (mostly) dev tool to make mod development a bit easier. It
supports [Mixin](https://github.com/Spongepowered/Mixin), it adds some more events
to the Event Bus, and it cleans up a bit of stuff.

**Want an event or a new feature? Submit an issue!**

## Adding this to your mod

To add this to your mod, add the maven and the mod dependency to your `build.gradle`:
```groovy
repositories {
    maven { url 'http://maven.concern.i.ng' }
}

dependencies {
    // ForgeGradle 3:
    fg.deobf implementation(group: 'sschr15', name: 'qol', version: '1.0.0')
    
    // ForgeGradle 2.3:
    deobfCompile 'sschr15:qol:1.0.0' 
}
```

## Examples

Now that I have added the mod to my dev environment, how might I do
something? Do I want to use Mixins? Do I want to use events? Find out here!

### Registering Mixin

[1 mixin JSON](src/main/java/sschr15/qol/QualityOfLife.java#L27):
```java
@Mod(modid = "example", name = "Example Mod", version = "1.2.3")
// This line here is the important one. You can define files to your Mixins here
@MixinConfig("example.mixins.json")
public class MyModClass {
    @Mod.EventHandler
    public void onInit(FMLPreInitializationEvent event) {
        // code can go here
    }
}
```

many JSONs:
```java
@Mod(modid = "example", name = "Example Mod", version = "1.2.3")
@MixinConfig({"example.mixins.json", "example2.mixins.json"})
public class MyModClass {
    @Mod.EventHandler
    public void onInit(FMLPreInitializationEvent event) {
        // code can go here
    }
}
```

It doesn't actually have to be on the mod class, but it's easiest to find it in source code that way.

### Using Events

```java
@Mod.EventBusSubscriber(modid = "example")
public class ExampleEventSubscriber {
    @SubscribeEvent
    public static void onPlayerDeath(PlayerDeathEvent event) {
        // the player died!
    }
}
```

Want to see the events? Check out `sschr15.qol.api.events`!

### QOLEvents

Forge events? No! Use your QOLEvents for some more... *questionable* content...
```java
@QOLSubscriber("example")
public class MySubscriber {
    @QOLSubscriber.TransformClass("net.minecraft.client.Minecraft")
    public static void transform(ClassNode node, String name) {
        // You can transform classes with this!
    }
}
```

### Other Stuffs

#### `GeneralUtils`

I have a `GeneralUtils` tool with a couple useful methods:

- `reflections()`: a [`Reflections`](https://github.com/ronmamo/reflections) object
- `getAnnotatedClasses(clazz)`: a `Set` of `Class<?>` that have `clazz` as an annotation
- `getSubclasses(clazz)`: a `Set` of `Class<?>` that are subclasses of `clazz`

## Building/Running

If on Windows, replace `./gradlew` with `gradlew.bat`

To build the mod, run `./gradlew build`

To run the mod in a development environment, run `./gradlew runClient` for a client
and `./gradlew runServer` for a server.

