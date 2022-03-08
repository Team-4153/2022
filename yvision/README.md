# YVision

Yvision is an extension of the multiServer code from FRC's WPILib
Suite. In addition to support for streaming from multiple cameras, it
provides functionality for tracking 202 vision target as well as
balls.

## Vision target tracking

The vision target tracking is enabled by adding an extra property in
the camera's custom properties JSON. If property with a name
"track_target" is present, and its value is "true", the camera is
used to track vision targets. An example of custom properties that
enable the vision target tracking:

`
[
                { "name": "track_target", "value": true },
                { "name": "target_h_low", "value": 60 },
                { "name": "target_s_low", "value": 200 },
                { "name": "target_v_low", "value": 136 },
                { "name": "target_h_high", "value": 95 },
                { "name": "target_s_high", "value": 255 },
                { "name": "target_v_high", "value": 255 }
                { "name": "target_min_area", "value": 0.00005 }
                { "name": "target_max_area", "value": 0.002 }
]
`

The tracking works best when the exposure time is low and the camera
is encircled by LEDs that emit light of a specific color. Properties
{hsv}_low define the lowest hue/saturation/value values for the
target. Respectively, {hsv}_high values define the maximum values for
the target color. The example above defines the full HSV range (Note:
OpenCV divides the Hue range [0:360] by two so it can fit in an 8-bit
value). Also, instead of range [0:100] for Saturation and Value,
OpenCV uses the range of [0:255]. The code also allows specifying the
range of the area of the targets (relative to the area of the image).

The code finds the average vertical position of all targets and
calculates the distance (in inches) based on that. It also calculates
the center of the targets relative to the center of the screen. It's a
value between -1 and 1, with 0 the target at the center.

The code creates a new folder on the SmartDashboard with the name of
the camera with "Target" appended at the end. It posts two values in
that folder:

- TargetDistance: distance to the target in inches.
- TargetOFf: offset of the target relative to the center of the image.


## Ball tracking

YVision also tracks balls. You can change only saturation and value
for them, not the hue. 

The code creates a new folder on the SmartDashboard with the name of
the camera with "Ball" appended at the end. It reads a value from that
folder with the name of "BallColor". If the value is 1, it tracks only
red balls. If it is 2, it tracks only blue balls. If 0 (default), it
tracks both.

The code posts three values in the same folder, related to the closest
ball (i.e. the ball with the highest radius):

- BallX: horizontal offset of the ball from the center (from -1 to 1).
- BallY: vertical offset of the ball from the center (from -1 to 1).
- BallRadius: fraction of the radius of the ball to the width of the
image (from 0 to 1).

Modifying the values

Once you are happy with the values shown in the browser, you have to
copy them to /boot/frc.json.

JSON is very picky, make sure that there are no commas after the last
entry.
