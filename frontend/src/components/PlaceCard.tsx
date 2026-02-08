import type { Place } from '../types/place';
import { PLACE_TYPE_LABEL } from '../types/placeLabels';


type Props = {
  place: Place;
  onOpen: (place: Place) => void;
  onDelete: (place: Place) => void;
};

export function PlaceCard({ place, onOpen, onDelete }: Props) {
  return (
    <article className="card" role="button" tabIndex={0} onClick={() => onOpen(place)}
      onKeyDown={(e) => {
        if (e.key === 'Enter' || e.key === ' ') onOpen(place);
      }}
      aria-label={`Открыть: ${place.title}`}
    >
      <button
        className="iconBtn"
        type="button"
        aria-label={`Удалить: ${place.title}`}
        onClick={(e) => {
          e.stopPropagation();
          onDelete(place);
        }}
      >
        🗑
      </button>

      <div className="cardTitle">{place.title}</div>
      <div className="cardMeta">
        <span className="pill">{PLACE_TYPE_LABEL[place.type]}</span>
        <span className="muted">★ {place.popularityScore}/5</span>
      </div>
    </article>
  );
}