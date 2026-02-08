import { useEffect, useMemo, useRef, useState } from 'react';
import type { Place, PlaceCreate, PlaceType } from '../types/place';

type Mode = 'add' | 'view' | 'edit';

type Props = {
  open: boolean;
  mode: Mode;
  place: Place | null; // для add будет null
  onClose: () => void;

  onCreate: (payload: PlaceCreate) => Promise<void>;
  onUpdate: (id: number, payload: PlaceCreate) => Promise<void>;
  onDelete: (id: number) => Promise<void>;
};

const TYPE_LABEL: Record<PlaceType, string> = {
  cathedral: 'Собор',
  church: 'Храм',
  museum: 'Музей',
  theatre: 'Театр',
  park: 'Парк',
  garden: 'Сад',
  palace: 'Дворец',
  bridge: 'Мост',
  monument: 'Памятник',
  embankment: 'Набережная',
  viewpoint: 'Смотровая площадка',
  street: 'Улица / проспект',
  cafe: 'Кафе',
  restaurant: 'Ресторан',
  bar: 'Бар',
  other: 'Другое',
};

const TYPE_OPTIONS: PlaceType[] = Object.keys(TYPE_LABEL) as PlaceType[];

function emptyForm(): PlaceCreate {
  return {
    title: '',
    type: 'cathedral',
    address: '',
    description: '',
    architect: null,
    popularityScore: 3,
  };
}

export function PlaceModal({
  open,
  mode,
  place,
  onClose,
  onCreate,
  onUpdate,
  onDelete,
}: Props) {
  const dialogRef = useRef<HTMLDialogElement | null>(null);
  const titleId = useMemo(
    () => `dlg-title-${Math.random().toString(16).slice(2)}`,
    []
  );

  const [innerMode, setInnerMode] = useState<Mode>(mode);
  const [form, setForm] = useState<PlaceCreate>(emptyForm());
  const [busy, setBusy] = useState(false);
  const [error, setError] = useState<string | null>(null);

  function syncFormFromPlace(targetMode: Mode) {
    if (targetMode === 'add') {
      setForm(emptyForm());
      return;
    }
    if (place) {
      const { id: _id, ...rest } = place;
      setForm(rest);
    }
  }

  useEffect(() => {
    const dlg = dialogRef.current;
    if (!dlg) return;

    if (open && !dlg.open) dlg.showModal();
    if (!open && dlg.open) dlg.close();
  }, [open]);

  useEffect(() => {
    if (!open) return;
    setInnerMode(mode);
    setBusy(false);
    setError(null);
    syncFormFromPlace(mode);
  }, [open, mode, place?.id]);

  useEffect(() => {
    if (!open) return;
    if (innerMode === 'view' && mode !== 'add') syncFormFromPlace('view');
  }, [place, open]);

  function set<K extends keyof PlaceCreate>(key: K, value: PlaceCreate[K]) {
    setForm((prev) => ({ ...prev, [key]: value }));
  }

  async function handleSave() {
    setError(null);
    setBusy(true);
    try {
      if (innerMode === 'add') {
        await onCreate(form);
      } else if (place) {
        await onUpdate(place.id, form);
      }
      onClose();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Ошибка');
    } finally {
      setBusy(false);
    }
  }

  async function handleDelete() {
    if (!place) return;
    const ok = confirm(`Удалить "${place.title}"?`);
    if (!ok) return;

    setError(null);
    setBusy(true);
    try {
      await onDelete(place.id);
      onClose();
    } catch (e) {
      setError(e instanceof Error ? e.message : 'Ошибка');
    } finally {
      setBusy(false);
    }
  }

  function handleCancelEdit() {
    syncFormFromPlace('view');
    setInnerMode('view');
    setError(null);
  }

  const readOnly = innerMode === 'view';

  return (
    <dialog
      ref={dialogRef}
      className="dialog"
      aria-labelledby={titleId}
      onCancel={(e) => {
        e.preventDefault();
        onClose();
      }}
      onClose={onClose}
    >
      <div className="dialogHeader">
        <h2 id={titleId} className="dialogTitle">
          {innerMode === 'add' ? 'Добавить место' : place?.title ?? 'Место'}
        </h2>

        <button
          className="iconBtn"
          type="button"
          onClick={onClose}
          aria-label="Закрыть"
          disabled={busy}
        >
          ✕
        </button>
      </div>

      {error && <div className="errorBox">{error}</div>}

      <form className="form" method="dialog" onSubmit={(e) => e.preventDefault()}>
        <label className="field">
          <span className="label">Название</span>
          <input
            value={form.title}
            onChange={(e) => set('title', e.target.value)}
            disabled={busy || readOnly}
          />
        </label>

        <label className="field">
          <span className="label">Тип</span>
          <select
            value={form.type}
            onChange={(e) => set('type', e.target.value as PlaceType)}
            disabled={busy || readOnly}
          >
            {TYPE_OPTIONS.map((t) => (
              <option key={t} value={t}>
                {TYPE_LABEL[t]}
              </option>
            ))}
          </select>
        </label>

        <label className="field">
          <span className="label">Адрес</span>
          <input
            value={form.address}
            onChange={(e) => set('address', e.target.value)}
            disabled={busy || readOnly}
          />
        </label>

        <label className="field">
          <span className="label">Архитектор</span>
          <input
            value={form.architect ?? ''}
            onChange={(e) => set('architect', e.target.value.trim() ? e.target.value : null)}
            disabled={busy || readOnly}
          />
        </label>

        <label className="field">
          <span className="label">Известность</span>

          <div className="scoreGroup" role="radiogroup" aria-label="Известность от 1 до 5">
            {[1, 2, 3, 4, 5].map((n) => (
              <button
                key={n}
                type="button"
                className={form.popularityScore === n ? 'scoreBtn scoreBtnActive' : 'scoreBtn'}
                onClick={() => set('popularityScore', n as 1 | 2 | 3 | 4 | 5)}
                disabled={busy || readOnly}
                aria-pressed={form.popularityScore === n}
              >
                {n}
              </button>
            ))}
          </div>
        </label>


        <label className="field">
          <span className="label">Описание</span>
          <textarea
            value={form.description}
            onChange={(e) => set('description', e.target.value)}
            disabled={busy || readOnly}
            rows={6}
          />
        </label>
      </form>

      <div className="dialogFooter">
        {innerMode !== 'add' && (
          <>
            {innerMode === 'view' ? (
              <button
                className="btn"
                type="button"
                onClick={() => {
                  setInnerMode('edit');
                  setError(null);
                }}
                disabled={busy}
              >
                Редактировать
              </button>
            ) : (
              <button
                className="btnGhost"
                type="button"
                onClick={handleCancelEdit}
                disabled={busy}
              >
                Отмена
              </button>
            )}

            <button
              className="btnDanger"
              type="button"
              onClick={handleDelete}
              disabled={busy}
            >
              Удалить
            </button>
          </>
        )}

        {innerMode === 'add' && (
          <button className="btnGhost" type="button" onClick={onClose} disabled={busy}>
            Отмена
          </button>
        )}

        {(innerMode === 'add' || innerMode === 'edit') && (
          <button className="btnPrimary" type="button" onClick={handleSave} disabled={busy}>
            {busy ? 'Сохранение...' : 'Сохранить'}
          </button>
        )}
      </div>
    </dialog>
  );
}