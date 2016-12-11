package com.camel.back2home.model;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Enumerators implements Serializable {

    public static final class Status {
        public static int Delete = 2;
        public static int Enable = 1;
        public static int Disable = 0;
    }

    public static final class AccountType {
        public static int Google = 3;
        public static int Twitter = 2;
        public static int Facebook = 1;
        public static int Email = 0;
    }


    /*
     *START ---PUSH NOTIFICATION WEB
	 */

    public static final class TipoJms {
        public static int NuevoEvento = 1;
        public static int EventoActualizado = 2;
        public static int EventoEliminado = 3;
        public static int NuevoTicket = 4;
        public static int TicketActualizado = 5;
        public static int TicketCancelado = 6;
        public static int Debate = 7;
        public static int TicketAceptado = 8;
        public static int DebateLeido = 9;
        public static int NuevaTarea = 10;
        public static int TareaAceptada = 11;
        public static int CambioFechaEvento = 12;
        public static int RecordatorioEvento = 13;
        public static int RecordatorioUsuario = 14;
        public static int NotificacionesCliente = 15;
    }

    public static final class TipoDebate {
        public static int DebateTicket = 1;
        public static int DebateDesarrollo = 2;
        public static int DebateTarea = 3;
    }

    public static final class TipoDireccion {
        public static int DireccionTicket = 1;
        public static int DireccionDesarrollo = 2;
        public static int DireccionTarea = 3;
        public static int DireccionEvento = 4;
    }

	/*
     *END --- PUSH NOTIFICATION WEB
	 */
}
