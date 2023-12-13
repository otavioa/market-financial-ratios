package br.com.mfr.service.ticket.discover;

public enum Ratio {
	
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
