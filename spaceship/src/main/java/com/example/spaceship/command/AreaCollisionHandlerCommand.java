package com.example.spaceship.command;


import com.example.spaceship.core.IoC;
import com.example.spaceship.model.Collidable;
import com.example.spaceship.model.Field;
import com.example.spaceship.model.Movable;
import com.example.spaceship.model.Vector;
import lombok.Builder;
import lombok.Data;

import java.util.Queue;

@Builder
@Data
public class AreaCollisionHandlerCommand implements Command {
    private Field field;
    private Queue<Command> commandQueue;
    private Movable movable;
    private int offset;
    private Vector previousPosition;

    @Override
    public void execute() {
        var initialArea = field.getArea(previousPosition);
        var areaAfterMove = field.getArea(movable.getPosition());
        // if initial area == areaAfterMove it will be still added in the end
        initialArea.movables().remove(movable);
        var movedCollidable = IoC.<Collidable>resolve("Adapter", Collidable.class.getName(), movable);

        var checkCollisionCommands = areaAfterMove.movables().stream()
                .map(mov -> {
                    var otherCollidable = IoC.<Collidable>resolve("Adapter", Collidable.class.getName(), mov);
                    return (Command) new CheckCollisionCommand(movedCollidable, otherCollidable);
                })
                .toList();
        areaAfterMove.movables().add(movable);

        commandQueue.addAll(checkCollisionCommands);
    }
}
