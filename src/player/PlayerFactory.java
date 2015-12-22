package player;


import game.COLOR;
import player.agents.*;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;


public class PlayerFactory {


    public final static String[] availablePlayers = {"DeepOthello", "EdgeEddie", "RandomRichard", "MinimizingMaria", "ForThello", "Skumtomtarna","ChattanoogaFlowmasters", "Ox5f3759df"};



    public void findAllPlayersInDir(final File directory) {
        for (final File fileEntry : directory.listFiles()) {
                System.out.println(fileEntry.getName());
        }
    }


    public Player newPlayer(String name, COLOR color) {


        System.out.println("Trying to find all classes in package...");

        Class[] classes = null;
        try {
            classes = getClasses("player.impl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(Class c : classes) {
            System.out.println("class: " + c.getSimpleName());
        }

        Player player = null;


        switch (name) {

            case "EdgeEddie":
                return new EdgeEddie(color);
            case "RandomRichard":
                return new RandomRichard(color);
            case "MinimizingMaria":
                return new MinimizingMaria(color);
            case "DeepOthello":
                return new DeepOthello(color);
            case "Skumtomtarna":
                return new Skumtomtarna(color);
            case "ChattanoogaFlowmasters":
                return new ChattanoogaFlowMasters(color);
            case "ForThello" :
                return new ForThello(color);
            case "Ox5f3759df" :
                return new Ox5f3759df(color);

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
