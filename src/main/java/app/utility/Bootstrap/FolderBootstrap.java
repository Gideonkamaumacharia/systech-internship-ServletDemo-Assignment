package app.utility.Bootstrap;


import jakarta.enterprise.context.Dependent;

@InitBootstrap
@Dependent
public class FolderBootstrap implements Bootstrap {

    @Override
    public void process() {
        System.out.println("==========================");
        System.out.println("Creating car document folders..");
        System.out.println("Creating picture folders.. for storing profiles");
        System.out.println("=========================");

    }
}

