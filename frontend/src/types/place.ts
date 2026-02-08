export type PlaceType =
  | 'cathedral'
  | 'church'
  | 'museum'
  | 'theatre'
  | 'park'
  | 'garden'
  | 'palace'
  | 'bridge'
  | 'monument'
  | 'embankment'
  | 'viewpoint'
  | 'street'
  | 'cafe'
  | 'restaurant'
  | 'bar'
  | 'other';

export type Place = {
  id: number;
  title: string;
  type: PlaceType;
  address: string;
  description: string;
  architect: string | null;
  popularityScore: 1 | 2 | 3 | 4 | 5;
};

export type PlaceCreate = Omit<Place, 'id'>;
export type PlaceUpdate = PlaceCreate;