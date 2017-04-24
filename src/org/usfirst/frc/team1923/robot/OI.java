package org.usfirst.frc.team1923.robot;

import org.usfirst.frc.team1923.robot.commands.drive.ResetEncoderCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftCommand;
import org.usfirst.frc.team1923.robot.commands.drive.ShiftOmniCommand;
import org.usfirst.frc.team1923.robot.commands.gear.GearCommand;
import org.usfirst.frc.team1923.robot.commands.gear.SlideCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.IndexerCommand;
import org.usfirst.frc.team1923.robot.commands.shooter.ShooterCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionGearAlignCommand;
import org.usfirst.frc.team1923.robot.commands.vision.VisionProcessing;
import org.usfirst.frc.team1923.robot.utils.controller.PS4Controller;
import org.usfirst.frc.team1923.robot.utils.controller.XboxController;

import edu.wpi.first.wpilibj.command.Command;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

    public PS4Controller driver;
    public XboxController op;

    public OI() {
        // Creates two ps4 controllers
        this.driver = new PS4Controller(RobotMap.DRIVER_CONTROLLER_PORT);
        this.driver.lt.setTriggerSensitivity(0.5);
        this.driver.rt.setTriggerSensitivity(0.5);

        this.driver.lb.whenActive(new ShiftCommand(false));
        this.driver.rb.whenActive(new ShiftCommand(true));

        this.driver.lt.whenActive(new ShiftOmniCommand(true));
        this.driver.rt.whenActive(new ShiftOmniCommand(false));

        this.driver.cross.whenActive(new ResetEncoderCommand());

        this.op = new XboxController(RobotMap.OP_CONTROLLER_PORT);

        this.op.x.whenActive(new SlideCommand());
        this.op.y.whenActive(new GearCommand());
        // this.op.b.whenActive(new GearSetHomeCommand());
        this.op.rb.whenActive(new ShooterCommand(6000));
        this.op.lb.whenActive(new ShooterCommand(0));
        this.op.a.whenActive(new IndexerCommand(true));
        this.op.b.whenActive(new IndexerCommand(false));

        // Vision Commands
        Command pegAlign = new VisionGearAlignCommand(false);
        this.driver.square.whileHeld(pegAlign);
        Command feederAlign = new VisionGearAlignCommand(true);
        this.driver.triangle.whileHeld(feederAlign);
        Command refresh = new VisionProcessing();
        this.driver.circle.whileHeld(refresh);
    }
}
