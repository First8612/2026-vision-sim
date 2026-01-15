# Vision Simulation Experiment

Vision and field positioning has been very troublesome for our team, and usually because we don't have a practice robot/camera free when we want to play with it. I wanted to see if I could simulate the vision environment so I could dabble with it at home.

## Uses:
- YAGSL for drivebase/field simulation.
    - YAGSL also uses MapleSim to do field physics simulation.
- PhotoVision's `VisionSystemSim`, which simulated what the cameras can see on the field.

## Example of it working
![Vision simulation screen capture](docs/screen-capture-0cb74.gif)



## Next Ideas:
- Simulate pose estimation from the cameras.
- An example of a mix of PV cameras, and Limelights.

