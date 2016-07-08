/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package task4all.utils;

import java.io.File;
import java.util.regex.Pattern;


public class UtilsMix {

    public static boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        return pattern.matcher(email).matches();
    }

    public static String crearRutaFichero(String path, String carpeta) {
        String ruta = path.substring(path.lastIndexOf(File.separator + carpeta), path.length());
        if (ruta.contains("\\")) {
            ruta = ruta.replaceAll("\\\\", "/");
        }
        return ruta;
    }

    public static String crearRutaParaLocalizarArchivo(String path) {
        if (File.separator.equals("\\")) {
            path = path.replaceAll("/", "\\\\");
        }
        return path;
    }
}
