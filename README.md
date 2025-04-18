# 🌍 Симуляция

![img.png](img.png)

Пошаговая 2D-симуляция, написанная на **Java**. Мир состоит из травоядных, хищников, травы и статичных препятствий. Существа двигаются с помощью алгоритма A*, взаимодействуют между собой по заранее заданным правилам.

---

## 🚀 Возможности

- 🧠 Поиск пути реализован с помощью алгоритма A*
- 🐑 Травоядные едят траву и избегают хищников
- 🐺 Хищники охотятся на травоядных
- 🌿 Трава появляется на карте в начале симуляции и добавляется каждый ход
- 🗻 Препятствия блокируют движение
- 🟥 Здоровье отображается индикатором жизни травоядных и хищников

---

## 🧩 Архитектура

- `Entity` — базовый класс всех объектов
- `Creature`, `Herbivore`, `Predator` — существа с поведением и здоровьем
- `WorldMap` — хранит карту и размещение объектов
- `AStarPathFinder` — реализует алгоритм A*
- `Simulation` — управляет ходами симуляции
- `Renderer` — отображает карту с помощью emoji
- `Action` — действия внутри симуляции: `InitAction`, `MoveCreaturesAction`, и др.

---

## 🔧 Как запустить

```bash
git clone https://github.com/CicadaN/simulation.git
cd simulation/src/main/java/io/example
java Main.java
