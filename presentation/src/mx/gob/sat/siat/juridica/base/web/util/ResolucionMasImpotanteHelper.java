package mx.gob.sat.siat.juridica.base.web.util;

import mx.gob.sat.siat.juridica.base.constantes.NumerosConstantes;
import mx.gob.sat.siat.juridica.base.helper.BaseHelper;
import mx.gob.sat.siat.juridica.rrl.dto.ResolucionImpugnadaDTO;
import mx.gob.sat.siat.juridica.rrl.web.controller.bean.utility.ResolucionesComparator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Helper que resalta la resolucion mas importante para la atencion
 * del asunto por parte del abogado y la autorizacion de la resolucion
 * 
 * @author softtek
 * @param <ResolucionImpugnadaDTO>
 * @since 23/01/2015
 */
@Component
public class ResolucionMasImpotanteHelper extends BaseHelper {

    /**
     * Numero de serie
     */
    private static final long serialVersionUID = 5972878221928219420L;

    /**
     * Metodo que obtiene la resolucion mas importante de una lista de
     * resoluciones
     * 
     * @param resolucionesList
     * @return impugnadaImportanteDTO
     */
    public ResolucionImpugnadaDTO obtenerResolucionImportante(List<ResolucionImpugnadaDTO> resolucionesList) {
        if (resolucionesList != null && !resolucionesList.isEmpty()) {
            if (resolucionesList.size() == 1) {
                return resolucionesList.get(0);
            }
            List<ResolucionImpugnadaDTO> resolucionesFiltro1 = filtroUnoDeterminates(resolucionesList);
            if (resolucionesFiltro1.size() == 1) {
                return resolucionesFiltro1.get(0);
            }
            List<ResolucionImpugnadaDTO> resolucionesFiltro2 = filtroDosNiveles(resolucionesFiltro1);
            if (resolucionesFiltro2.size() == 1) {
                return resolucionesFiltro2.get(0);
            }
            ResolucionImpugnadaDTO resolucionesFiltro3 = filtroTresMonto(resolucionesFiltro2);

            return resolucionesFiltro3;
        }
        else {

            return null;
        }
    }

    private ResolucionImpugnadaDTO filtroTresMonto(List<ResolucionImpugnadaDTO> resolucionesFiltro2) {

        ResolucionesComparator resolucionesComparator = new ResolucionesComparator();
        Collections.sort(resolucionesFiltro2, resolucionesComparator);
        int i = 0;
        for (ResolucionImpugnadaDTO resolu : resolucionesFiltro2) {

            if (i == 0) {
                return resolu;
            }

        }
        return null;
    }

    /**
     * Metodo que divide las resoluciones por Determinantes/No
     * determinantes de credito
     * 
     * @param resolucionesList
     */
    private List<ResolucionImpugnadaDTO> filtroUnoDeterminates(List<ResolucionImpugnadaDTO> resolucionesList) {
        List<ResolucionImpugnadaDTO> resolucionesDeterminantes = new ArrayList<ResolucionImpugnadaDTO>();
        List<ResolucionImpugnadaDTO> resolucionesNoDeterminantes = new ArrayList<ResolucionImpugnadaDTO>();

        for (ResolucionImpugnadaDTO resolucion : resolucionesList) {
            if (!resolucion.getDeterminanteCred()) {
                resolucionesDeterminantes.add(resolucion);
            }
            else {
                resolucionesNoDeterminantes.add(resolucion);
            }
        }

        if (!resolucionesDeterminantes.isEmpty()) {

            return resolucionesDeterminantes;

        }
        else {
            return resolucionesNoDeterminantes;
        }

    }

    /**
     * Metodo que compara las atributos de las resoluciones para
     * determinar cual es la resolucion mas importante
     * 
     * @param resoluciones
     */
    private List<ResolucionImpugnadaDTO> filtroDosNiveles(List<ResolucionImpugnadaDTO> resolucionesFiltro1) {
        Iterator<ResolucionImpugnadaDTO> iteratorResoluciones = resolucionesFiltro1.iterator();
        List<ResolucionImpugnadaDTO> resolucionesNivelImportante = new ArrayList<ResolucionImpugnadaDTO>();

        for (Integer nivel = 1; nivel <= NumerosConstantes.NUEVE; nivel = nivel + 1) {
            for (ResolucionImpugnadaDTO res : resolucionesFiltro1) {
                if (nivel.intValue() == res.getAutoridadEmisora().getNivel().intValue()) {

                    while (iteratorResoluciones.hasNext()) {
                        ResolucionImpugnadaDTO resolucionesBarrer = iteratorResoluciones.next();
                        if (resolucionesBarrer.getAutoridadEmisora().getNivel().intValue() == nivel.intValue()) {
                            resolucionesNivelImportante.add(resolucionesBarrer);
                        }

                    }

                }
            }
        }
        return resolucionesNivelImportante;
    }

}
