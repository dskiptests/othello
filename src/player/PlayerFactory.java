package player;


import game.Color;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;
import java.util.*;
import java.util.jar.*;


public class PlayerFactory {


    public String[] availablePlayers;
    private Map<String, Class> playerMap;
    private final String AGENT_PACKAGE = "player.agents";


    public PlayerFactory() {
        getAgentClassesFromPackage();
    }

    public Agent newPlayer(String name, Color color) {
        Class c = playerMap.get(name);
        Agent agent = mapClassToPlayerObject(playerMap.get(name), color);

        return agent;
    }

    private void getAgentClassesFromPackage() {
        this.playerMap = new HashMap<String, Class>();

        Class[] classes = null;
        try {
            classes = getClasses(AGENT_PACKAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> playerList = new LinkedList<String>();

        for(Class c : classes) {
            if(Agent.class.isAssignableFrom(c)) {
                this.playerMap.put(c.getSimpleName(), c);
                playerList.add(c.getSimpleName());
            }
        }
        this.availablePlayers = new String[playerList.size()];
        for(int i = 0; i < playerList.size(); i++) {
            availablePlayers[i] = playerList.get(i);
        }

    }

    private Agent mapClassToPlayerObject(Class<Agent> c, Color color) {

        Constructor<Agent> cons = null;
        try {
            cons = c.getConstructor(Color.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Agent agent = null;
        try {
            agent = (Agent) cons.newInstance(color);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return agent;
    }

    private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        URL resource = classLoader.getResource(path);
        
        List<Class> classes = new ArrayList<Class>();
        // in case we run with: java -jar othello-xyz.jar
        if (resource.getProtocol().equals("jar")) {
            String jarPath = resource.getPath().substring(5, resource.getPath().indexOf("!")); //strip out only the JAR file
            JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
            Enumeration<JarEntry> entries = jar.entries(); //gives ALL entries
            while(entries.hasMoreElements()) {
                String name = entries.nextElement().getName();
                if (name.endsWith("/")) { continue; }
                if (name.startsWith(path)) { //filter according to the path
                    String entryPkg = name.substring(path.length()+1).replace('/', '.');
                    // Remove ".class"
                    entryPkg=entryPkg.substring(0, entryPkg.length() - 6);
                    classes.add(Class.forName(packageName + "." + entryPkg));
                }
            }
        }
        else {
            classes.addAll(findClasses(new File(resource.getFile()), packageName));
        }
            
        return classes.toArray(new Class[classes.size()]);
    }

    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists()) {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                assert !file.getName().contains(".");
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }

}
