package physics;

import javafx.util.Pair;

import java.util.*;

/**
 * Represents a physics environment.
 */
public class Environment {
    /**
     * The amount of ticks per second.
     */
    public final int TICKRATE = 100;
    /**
     * The maximum speed possible in this environment.
     */
    public final int SPEEDOFLIGHT = 100;

    /**
     * The Gravity value of this environment
     */
    private double gravity = 9.81;
    /**
     * The friction coefficient in this environment.
     */
    private double friction = 1;

    /**
     * The drag coefficient in this environment.
     */
    private double drag = 1;

    private List<Prop> props = new ArrayList<Prop>();
    private Map<Prop, Map<Vec2d,Integer>> forces = new HashMap<>();

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getDrag() {
        return drag;
    }

    public void setDrag(double drag) {
        this.drag = drag;
    }

    public void tick() {
        Map<Prop, Map<Vec2d,Integer>> newForces = new HashMap<>();
        for(Map.Entry<Prop, Map<Vec2d,Integer>> e : forces.entrySet()) {
            //Loop through all props
            Prop prop = e.getKey();
            Map<Vec2d,Integer> forcePairs = e.getValue();
            Map<Vec2d,Integer> newForcePairs = new HashMap<>();
            for(Map.Entry<Vec2d,Integer> forceEntry : forcePairs.entrySet()) {
                //For each prop, look through all forces.
                Vec2d force = forceEntry.getKey();
                int ticks = forceEntry.getValue();
                if(ticks > 0) {
                    ticks--;
                    newForcePairs.put(force,ticks);
                    prop.applyForce(force);
                }
            }
            if(newForcePairs.size() > 0) {
                newForces.put(prop,newForcePairs);
            }
        }
        forces = newForces;

        for(Prop p : props) {
            p.tick();
        }
    }

    public List<Prop> getProps() {
        return props;
    }

    public void spawnProp(Prop newProp) {
        props.add(newProp);
    }

    public void applyForceTime(Prop prop, Vec2d force, int ticks) {
        if(props.contains(prop)) {
            Map<Vec2d,Integer> forcePairs;
            if(forces.containsKey(prop)) {
                //Prop already exists in force list.
                forcePairs = forces.get(prop);
                int newTicks = ticks;
                if(forcePairs.containsKey(force)) {
                    //Force already exists in force list.
                    newTicks += forcePairs.get(force);
                }
                forcePairs.put(force,newTicks);
            } else {
                //Prop does not yet exist in force list.
                forcePairs = new HashMap<>();
                forcePairs.put(force,ticks);
                forces.put(prop,forcePairs);
            }
        }
    }
}
