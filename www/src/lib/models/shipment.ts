export enum ShipmentPriority {
	LOW = 'LOW',
	MEDIUM = 'MEDIUM',
	HIGH = 'HIGH'
}

export interface CreateShipmentRequest {
	name: string;
	originAddress: string;
	originLat: number;
	originLng: number;
	minTemp: number;
	maxTemp: number;
	temperature: number;
	destinationAddress: string;
	destinationLat: number;
	destinationLng: number;
	estimatedPickupTime: string;
	estimatedDeliveryTime: string;
	priority: ShipmentPriority;
	vehicleIds: number[];
}

// Will seperate later
export interface NominatimResult {
	place_id: number;
	licence: string;
	osm_type: string;
	osm_id: number;
	boundingbox: string[];
	lat: string;
	lon: string;
	display_name: string;
	class: string;
	type: string;
	importance: number;
}
