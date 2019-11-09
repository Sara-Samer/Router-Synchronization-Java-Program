class Router{
	private Device[] listOfConnections;
	private LabSemaphore semaphore;
	public Router(int value) {
		semaphore = new LabSemaphore(value);
		listOfConnections = new Device[value];
		for(int i = 0 ;  i < value ; i++)
			listOfConnections[i] = null;
	}
	@SuppressWarnings("static-access")
	public int occupy(Device device) {
		int index = 0;
		for(int i = 0 ; i < listOfConnections.length ; i++) {
			if(listOfConnections[i] == null) {
				System.out.println("Connection " + (i + 1) + ": " + "(" + device.name + ")" + " (" + device.type +")" + " arrived.");
				listOfConnections[i] = device;
				index = i;
				semaphore.Lock();
				return index;
			}
		}
		System.out.println("(" + device.name + ")" + " (" + device.type +")" + " arrived and waiting.");
		semaphore.Lock();
		for(int i = 0 ; i < listOfConnections.length ; i++) {
			if(listOfConnections[i] == null) {
				listOfConnections[i] = device;
				index = i;
			}
		}
		return index;
	}
	
	public void release(Device device) {
		for(int i = 0 ; i < listOfConnections.length ; i++) {
			if(listOfConnections[i] == device) {
				System.out.println("Connection " + (i + 1) + ": "+ device.name + " (" + device.type + ") "  + "Logged out");
				listOfConnections[i] = null;
				semaphore.Release();
			}
		}
	}
}