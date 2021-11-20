package me.tyler.game.event;

import me.tyler.game.GamePanel;
import me.tyler.game.event.impl.RowClickEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class EventHandler {

    private final GamePanel gamePanel;
    private final Map<Class<? extends Event>, Set<Method>> listeners;

    public EventHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        this.listeners = new HashMap();
    }

    public void registerListener(Listener listener) {
        Class<? extends Listener> listenerClazz = listener.getClass();
        Set<Method> methods = new HashSet<>();
        for(Method method : listenerClazz.getMethods()) {
            if(method.isAnnotationPresent(GameEvent.class)) {
                methods.add(method);
            }
        }

        methods.stream()
                .filter(method -> method.getParameterCount() > 0)
                .filter(method -> {
                    Class<?> parameter = method.getParameterTypes()[0];
                    return parameter.getSuperclass() == Event.class;
                })
                .forEach(method -> {
                    Class<? extends Event> eventTypeParameter = (Class<? extends Event>) method.getParameterTypes()[0];
                    Set<Method> activeListeners = this.listeners.getOrDefault(eventTypeParameter, new HashSet<>());
                    activeListeners.add(method);
                    this.listeners.put(eventTypeParameter, activeListeners);
                });

    }

    public void handleEvent(Event event) {
        Set<Method> listeners = this.listeners.getOrDefault(event.getClass(), null);
        if(listeners == null) return;
        listeners.forEach(listener -> {
            try {
                listener.invoke(listener.getDeclaringClass().newInstance(), event);
            } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
                e.printStackTrace();
            }
        });
    }
}
