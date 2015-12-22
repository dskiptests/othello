package player;


import game.COLOR;
import player.agents.*;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.*;


public class PlayerFactory {


    public String[] availablePlayers;
    private Map<String, Class> playerMap;
    private final String PACKAGE = "player.agents";


    public PlayerFactory() {
        getAgentsFromPackage();
    }

    public Player newPlayer(String name, COLOR color) {

        System.out.println("Requested player " + name);
        System.out.println(playerMap.keySet());


        Class c = playerMap.get(name);
        System.out.println("Class " + c.getSimpleName());

        Player player = mapClassToPlayerObject(playerMap.get(name), color);

        return player;
    }


    private void getAgentsFromPackage() {
        this.playerMap = new HashMap<String, Class>();

        Class[] classes = null;
        try {
            classes = getClasses(PACKAGE);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> playerList = new LinkedList<String>();

        for(Class c : classes) {
            if(Player.class.isAssignableFrom(c)) {
                this.playerMap.put(c.getSimpleName(), c);
                playerList.add(c.getSimpleName());
            }
        }

        this.availablePlayers = new String[playerList.size()];
        for(int i = 0; i < playerList.size(); i++) {
            availablePlayers[i] = playerList.get(i);
        }

    }


    private Player mapClassToPlayerObject(Class<Player> c, COLOR color) {

        Constructor<Player> cons = null;
        try {
            cons = c.getConstructor(COLOR.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        Player player = null;
        try {
            player = (Player) cons.newInstance(color);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return player;
    }

    private static Class[] getClasses(String packageName)
            throws ClassNotFoundException, IOException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        assert classLoader != null;
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            dirs.add(new File(resource.getFile()));
        }
        ArrayList<Class> classes = new ArrayList<Class>();
        for (File directory : dirs) {
            classes.addAll(findClasses(directory, packageName));
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
