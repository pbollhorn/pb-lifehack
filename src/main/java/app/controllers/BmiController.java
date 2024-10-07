package app.controllers;

import app.persistence.ConnectionPool;
import io.javalin.Javalin;
import io.javalin.http.Context;


public class BmiController {

    public static void addRoutes(Javalin app, ConnectionPool connectionPool) {
        app.get("/bmi", ctx -> index(ctx, connectionPool));
        app.post("/bmi/calculate", ctx -> calculate(ctx, connectionPool));
    }

    private static void index(Context ctx, ConnectionPool connectionPool) {
        ctx.render("/bmi/index.html");
    }

    private static void calculate(Context ctx, ConnectionPool connectionPool) {

        // Retrieve height and weight from form submission
        float height = Float.parseFloat(ctx.formParam("height"));
        float weight = Float.parseFloat(ctx.formParam("weight"));

        // Calculate bmi and get bmi classification
        float bmi = weight / ((height / 100) * (height / 100));
        String bmiClassification = getBmiClassification(bmi);

        // Set attributes and render result page
        ctx.attribute("height", height);
        ctx.attribute("weight", weight);
        ctx.attribute("bmi", bmi);
        ctx.attribute("bmiClassification", bmiClassification);
        ctx.render("bmi/result.html");
    }


    private static String getBmiClassification(float bmi) {

        String bmiClassification = "Severe Thinness";

        if (bmi > 16) {
            bmiClassification = "Moderate Thinness";
        }
        if (bmi > 17) {
            bmiClassification = "Mild Thinness";
        }
        if (bmi > 18.5) {
            bmiClassification = "Normal";
        }
        if (bmi > 25) {
            bmiClassification = "Overweight";
        }
        if (bmi > 30) {
            bmiClassification = "Obese Class I";
        }
        if (bmi > 35) {
            bmiClassification = "Obese Class II";
        }
        if (bmi > 40) {
            bmiClassification = "Obese Class III";
        }

        return bmiClassification;

    }

}