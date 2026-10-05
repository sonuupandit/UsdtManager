# USDT Money Manager (Android - Kotlin + Supabase Cloud)

Native Android app for daily USDT buy/sell business tracking.
Data cloud (Supabase) me safe rehta hai - phone kharaab ho toh bhi data safe.

## Features
- **Dashboard**: aaj ka sell/buy, profit, USDT stock, weekly & monthly summary
- **Add**: 10 second me entry - SELL @106 / BUY @103, auto USDT qty calc
- **Ledger**: pura history, delete support
- **Reports**: Aaj / 7 din / Mahina / All time - profit, qty, counts

## Setup (5 steps)

### 1. Supabase project banao
- https://supabase.com pe jao -> New Project (free plan)
- Project banne ke baad **SQL Editor** kholo

### 2. Ye SQL run karo (SQL Editor me paste karo -> Run):
```sql
create table transactions (
  id bigint generated always as identity primary key,
  type text not null,
  customer text default '',
  amount_inr double precision not null,
  rate double precision not null,
  usdt_qty double precision not null,
  note text default '',
  local_date text not null
);

-- Personal app hai (sirf tum use karoge), isliye anon key ko access de rahe hain.
-- Agar security chahiye toh Supabase Auth + RLS enable karo.
grant all on table transactions to anon, authenticated;
alter table transactions enable row level security;

create policy "anon full access" on transactions
  for all to anon using (true) with check (true);
```

### 3. API keys dalo
- Supabase -> Project Settings -> API
- `Project URL` aur `anon public` key copy karo
- File kholo: `app/src/main/java/com/sonu/usdtmanager/data/SupabaseConfig.kt`
- `SUPABASE_URL` aur `SUPABASE_ANON_KEY` me paste karo

### 4. Android Studio me kholo
- Android Studio -> Open -> ye folder select karo
- Gradle sync hoga (internet chahiye, 2-5 min)

### 5. Run
- Phone connect karo (USB debugging on) ya emulator
- Run button dabao - app install ho jayegi

## Daily Routine (app ke saath)
1. Har customer ka paisa aane par turant "Add" me SELL entry
2. Jab upstream se USDT kharido, BUY entry
3. Sham ko Dashboard dekho - aaj ka profit wahan hai

## Notes
- Profit calculation: sell revenue minus (sold qty × average buy rate)
- Ye app books/tracking ke liye hai; tax (30% + 1% TDS on crypto) apne CA se confirm karo
