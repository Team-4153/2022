# Climber

## Detection Sequence

### Automated Sequence

**STATUS:** Not Started

```mermaid
graph TD
A[Camera Sees Tape]
A -- Math --> B(Display Message to Driver)
B --> C(Accurate)
B --> D(Innacurate)
C --> G(Start Climb)
D -- More Math --> F[\Auto-Plot Adjust/]
F --> B
F --> H[Adjust Fails... Well F***]
```

### Manual Sequence

**STATUS:** Not Started

```mermaid
graph TD
A[Driver Presses View Camera Button]
B[Camera Displayed]
C[Driver Adjusts Bot Manually]
A --> B
B --> C
```

## Climb Sequence

### Start Sequence
**STATUS:** Not Started
```mermaid
graph TD
A[Start Sequence] --> B
B[Climb Start: Accuracy Assumed] --> C[Pre-programmed motor sequence]
A --> D[Climb Start: Accuracy not assumed]
D --> E[Driver Adjust]
E --> C
```

### Motor Sequence (No Winch)
**STATUS:** Not Started
```mermaid
graph TD
A{Motor On}
A --> B{{Wait For Limit Switch}}
B --> C(Switch Triggered)
B --> D[System Times Out Approx 20s]
C --> E(Motor Stops)
D --> E
```

### Motor Sequence (With Winch)

```mermaid
graph TD
A{Motor On}
A --> B{{Wait For Limit Switch}}
B --> C(Switch Triggered)
B --> D[System Times Out Approx 20s]
D --> G(Error Reported to Driver)
G --> H(Motor Stops)
C --> E(Motor Stops)
E --> F[Piston Locks Winch]
```