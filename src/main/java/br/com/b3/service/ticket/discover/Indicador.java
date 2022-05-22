package br.com.b3.service.ticket.discover;

public enum Indicador {
	
	VALUE {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithValue();
		}
	},
	
	PL {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithPL();
		}
	},
	
	ROE {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithROE();
		}
	},
	
	LPA {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithLPA();
		}
	},
	
	VPA {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithVPA();
		}
	},
	
	DY {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithDY();
		}
	},
	
	PVP {
		@Override
		public DocumentDiscoveryRule getRule() {
			return new WithPVP();
		}
	};

	public abstract DocumentDiscoveryRule getRule();
}
